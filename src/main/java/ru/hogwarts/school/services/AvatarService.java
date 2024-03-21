package ru.hogwarts.school.services;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.models.Avatar;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {
    @Value("${students.avatar.dir.path}")
    private String avatarDir;
    private final StudentService studentService;
    private final AvatarRepository avatarRepository;
    Logger logger = LoggerFactory.getLogger(StudentService.class);
    public AvatarService(AvatarRepository avatarRepository,StudentService studentService){
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }
    public void uploadAvatar(Long id, MultipartFile avatar) throws IOException {
        logger.info("Загрузка аватарки для студента с id " + id);
        Student student = studentService.findStudentById(id);
        Path filePath = Path.of(avatarDir, id + "." + getExtension(Objects.requireNonNull(avatar.getOriginalFilename())));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatar.getInputStream();
                OutputStream os = Files.newOutputStream(filePath,CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar1 = findAvatar(id);
        avatar1.setStudent(student);
        avatar1.setFilePath(filePath.toString());
        avatar1.setFileSize(avatar.getSize());
        avatar1.setMediaType(avatar.getContentType());
        avatar1.setData(generateAvatarPreview(filePath));

        avatarRepository.save(avatar1);
    }
    public Avatar findAvatar(Long id){
        return avatarRepository.findByStudentId(id).orElse(new Avatar());
    }
    private byte[] generateAvatarPreview(Path filePath) throws IOException{
        logger.debug("Изменение размера аватарки для сохранения в БД");
        try (
                InputStream is = Files.newInputStream(filePath);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            BufferedImage image = ImageIO.read(bis);
            int height = image.getHeight() / image.getWidth() / 100;
            BufferedImage preview = new BufferedImage(100,height,image.getType());
            Graphics graphics = preview.createGraphics();
            graphics.drawImage(image,0,0,100,height,null);
            graphics.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()),baos);
            return baos.toByteArray();
        }
    }
    private String getExtension(String fileName){
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
    public List<Avatar> getAvatars(Integer page, Integer size){
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return avatarRepository.findAll(pageRequest).getContent();
    }
}
