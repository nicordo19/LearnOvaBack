package rncp.backend.sevice;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rncp.backend.entity.Video;
import rncp.backend.repository.VideoRepository;

@Service
public class VideoService {

    private final CloudinaryService cloudinaryService;
    private final VideoRepository videoRepository;

    public VideoService(CloudinaryService cloudinaryService, VideoRepository videoRepository) {
        this.cloudinaryService = cloudinaryService;
        this.videoRepository = videoRepository;
    }

    public String uploadVideo(MultipartFile file) {
        String videoUrl = cloudinaryService.uploadVideo(file);

        Video video = new Video();
        video.setUrl(videoUrl);
        videoRepository.save(video);

        return videoUrl;
    }
}
