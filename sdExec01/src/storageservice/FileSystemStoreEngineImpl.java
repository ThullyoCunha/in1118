package storageservice;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created by tjamir on 30/03/17.
 */
public class FileSystemStoreEngineImpl implements StoreEngine{




    private static final String BASE_FOLDER_NAME = "storage";
    private Path baseFolder;


    public FileSystemStoreEngineImpl(){
        baseFolder = Paths.get(BASE_FOLDER_NAME);
        try {
            Files.createDirectories(baseFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public ValueObject get(String key) throws IOException {

        Path path  = Paths.get(BASE_FOLDER_NAME).resolve(key);
        if(Files.exists(path)){
            try(FileChannel fileChannel=FileChannel.open(path, StandardOpenOption.READ)){
                long fileSize = Files.size(path);
                MappedByteBuffer map = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileSize);
                long version=map.getLong();
                int size=map.getInt();
                byte[] data=new byte[size];
                map.get(size);
                ValueObject valueObject = new ValueObject();
                valueObject.setContent(data);
                valueObject.setVersion(version);

            }
        }
        return null;
    }

    @Override
    public void save(String key, ValueObject valueObject) throws IOException {
        Path path = Paths.get(BASE_FOLDER_NAME).resolve(key);
        try (FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0L, valueObject.getContent().length+1024);
            buffer.putLong(valueObject.getVersion());
            buffer.putInt(valueObject.getContent().length);
            buffer.put(valueObject.getContent());
        }
    }
}
