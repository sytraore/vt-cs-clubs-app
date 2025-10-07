package com.vtcsclubs.backend.bootstrap;

import com.vtcsclubs.backend.models.AdminUser;
import com.vtcsclubs.backend.models.Club;
import com.vtcsclubs.backend.models.Sponsor;
import com.vtcsclubs.backend.models.Tag;
import com.vtcsclubs.backend.repositories.AdminUserRepository;
import com.vtcsclubs.backend.repositories.ClubRepository;
import com.vtcsclubs.backend.repositories.SponsorRepository;
import com.vtcsclubs.backend.repositories.TagRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner{
    private final ClubRepository clubRepository;
    private final TagRepository tagRepository;
    private final SponsorRepository sponsorRepository;
    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(ClubRepository clubRepository, TagRepository tagRepository, SponsorRepository sponsorRepository, AdminUserRepository adminUserRepository, PasswordEncoder passwordEncoder) {
        this.clubRepository = clubRepository;
        this.tagRepository = tagRepository;
        this.sponsorRepository = sponsorRepository;
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (clubRepository.count() == 0) {
            System.out.println("Loading sample data...");

            // Create a Club
            Club vthacks = new Club("VTHacks", "Virginia Tech's largest hackathon.", "contact@vthacks.com");
            clubRepository.save(vthacks);
            System.out.println("Created Club: VTHacks (ID: " + vthacks.getClubId() + ")");

            // Create some Tags
            Tag pythonTag = new Tag("Python", "Technology");
            Tag aiTag = new Tag("AI/ML", "Field");
            Tag workshopTag = new Tag("Workshop", "EventType");
            tagRepository.save(pythonTag);
            tagRepository.save(aiTag);
            tagRepository.save(workshopTag);
            System.out.println("Created Tags: Python (ID: " + pythonTag.getTagId() + "), AI/ML (ID: " + aiTag.getTagId() + "), Workshop (ID: " + workshopTag.getTagId() + ")");

            // Create some Sponsors
            Sponsor coStar = new Sponsor("CoStar");
            Sponsor google = new Sponsor("Google");
            sponsorRepository.save(coStar);
            sponsorRepository.save(google);
            System.out.println("Created Sponsors: CoStar (ID: " + coStar.getSponsorId() + "), Google (ID: " + google.getSponsorId() + ")");

            // Create an AdminUser for the Club
            AdminUser admin = new AdminUser("admin@vthacks.com", passwordEncoder.encode("password"), vthacks);
            adminUserRepository.save(admin);
            System.out.println("Created Admin: admin@vthacks.com with password 'password'");

            System.out.println("Sample data loaded.");
        }
    }
}
