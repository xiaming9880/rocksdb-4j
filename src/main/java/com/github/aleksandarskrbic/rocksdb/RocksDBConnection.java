package com.github.aleksandarskrbic.rocksdb;

import com.github.aleksandarskrbic.rocksdb.configuration.RocksDBConfiguration;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.Files.createDirectories;

public abstract class RocksDBConnection {

    private static final Logger LOGGER = LoggerFactory.getLogger(RocksDBConnection.class);

    protected RocksDB rocksDB;

    public RocksDBConnection(final RocksDBConfiguration configuration) {
        RocksDB.loadLibrary();

        try {
            final Options options = new Options().setCreateIfMissing(true);
            final String root = System.getProperty("user.dir");
            final String rocksDirectory = root + "/src/main/resources/" + configuration.path() + "/" + configuration.name();
            final Path path = Paths.get(rocksDirectory);
            createDirectories(path);
            rocksDB = RocksDB.open(options, path.toString());
        } catch (final Exception exception) {
            exception.printStackTrace();
            LOGGER.error("Exception occurred during RocksDB initialization. Shutting down application...");
            System.exit(1);
        }
    }
}
