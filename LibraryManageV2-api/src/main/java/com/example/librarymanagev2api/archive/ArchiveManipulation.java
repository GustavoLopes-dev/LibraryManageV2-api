package com.example.librarymanagev2api.archive;

import java.io.IOException;

public interface ArchiveManipulation {
    void reader() throws IOException;

    void writer() throws IOException;

    void delete() throws IOException;
}
