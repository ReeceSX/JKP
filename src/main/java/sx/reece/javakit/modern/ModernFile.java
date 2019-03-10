package sx.reece.javakit.modern;

import sx.reece.javakit.csharp.BitConverter;
import sx.reece.javakit.stream.ModernInputStreamReader;
import sx.reece.javakit.stream.ModernOutputStreamWriter;
import sx.reece.javakit.stream.RuntimeIOException;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * | Author: Reece Wilson (Reece.SX @not_rsx)
 * | Type: Work in progress
 * | License:
 * |  Copyright (C) Reece.SX - MIT
 */
public class ModernFile extends File {

    public ModernFile(String pathname) {
        super(pathname);
    }

    public static byte[] readAllBytes(String path) {
        try {
            return Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            return new byte[0];
        }
    }

    public static String cleanPath(String path) {
        return path.replace("\\", File.separator).replace("/", File.separator); //OS Dependent Java FileSystem will do this for us, but it's nice to fix this beforehand.
    }

    public static String readAllString(String path) {
        return new String(readAllBytes(path));
    }

    public static void writeAllString(String path, String data) {
        getFileOrCreateNew(path);
        writeAllBytes(path, data.getBytes());
    }

    public static void writeAllBytes(String path, byte[] data) {
        try {
            getFileOrCreateNew(path);
            Files.write(Paths.get(path), data);
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    public static ModernFile getFileOrCreateNew(String path) {
        ModernFile file = new ModernFile(path);
        path = cleanPath(path);
        if (path.contains(File.separator))
            getDirOrCreateNew(path.substring(0, path.lastIndexOf(File.separator)));
        try {
            if (!file.exists()) if (!(file.createNewFile())) throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return file;
    }

    public static File getDirOrCreateNew(String path) {
        path = cleanPath(path);
        File file = new File(path);
        try {
            if (!file.exists())
                if (!(file.mkdirs())) return null;
        } catch (Exception e) {
            return null;
        }
        return file;
    }

    public static ModernFile get(File file) {
        return new ModernFile(file.getAbsolutePath());
    }

    public static ModernFile get(String file) {
        return new ModernFile(file);
    }

    public byte[] readAllBytes() {
        return readAllBytes(this.getAbsolutePath());
    }

    public String readAllString() {
        return readAllString(this.getAbsolutePath());
    }

    public void writeAllString(String data) {
        getFileOrCreateNew(this.getAbsolutePath());
        writeAllString(this.getAbsolutePath(), data);
    }

    public void writeAllBytes(byte[] data) {
        getFileOrCreateNew(this.getAbsolutePath());
        writeAllBytes(this.getAbsolutePath(), data);
    }

    public ModernFile getFileOrCreateNew() {
        return getFileOrCreateNew(this.getAbsolutePath());
    }

    public ModernOutputStreamWriter outputStream() throws FileNotFoundException, RuntimeIOException {
        return new ModernOutputStreamWriter(new FileOutputStream(getFileOrCreateNew(this.getAbsolutePath())));
    }

    public ModernInputStreamReader inputStream() throws FileNotFoundException {
        return new ModernInputStreamReader(new FileInputStream(this));
    }

    public File getDirOrCreateNew() {
        return getDirOrCreateNew(this.getAbsolutePath());
    }

    public ModernBuffer getModernBuffer() {
        return new ModernBuffer(readAllBytes(this.getAbsolutePath()));
    }

    public String getFileName() {
        String fileName = getAbsolutePath();
        return fileName.substring(fileName.lastIndexOf(File.separator) + 1);
    }

    @Override
    public String toString() {
        byte[] contents = readAllBytes();
        StringBuilder stringBuffer = new StringBuilder()
                .append("File: ")
                .append(getFileName())
                .append("\r\n")
                .append("Length: ")
                .append(contents.length);
        if (contents.length < 1024 * 4) {
            stringBuffer
                    .append("\r\n")
                    .append("Contents Hex: ").append(BitConverter.toString(contents));

            try {
                CharsetDecoder UTF8Decoder =
                        Charset.forName("UTF8").newDecoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);

                CharBuffer out = CharBuffer.wrap(new char[contents.length]);
                CoderResult result = UTF8Decoder.decode(ByteBuffer.wrap(contents), out, true);
                if (result.isError() || result.isMalformed()) throw new Exception();

                stringBuffer.append("\r\n").append("String: ").append(new String(contents));
            } catch (Exception e) {
                stringBuffer.append("\r\n").append("Not a UTF8 file!");
            }
        }
        return stringBuffer.toString();
    }
}
