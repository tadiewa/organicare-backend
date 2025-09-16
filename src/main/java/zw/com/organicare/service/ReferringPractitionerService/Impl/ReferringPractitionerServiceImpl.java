/**
 * @author : tadiewa
 * date: 9/11/2025
 */


package zw.com.organicare.service.ReferringPractitionerService.Impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.com.organicare.constants.Role;
import zw.com.organicare.dto.referringPrac.ReferringPracDto;
import zw.com.organicare.exception.UserNotFound;
import zw.com.organicare.model.ReferringPractitioner;
import zw.com.organicare.model.User;
import zw.com.organicare.repository.ReferringPractitionerRespository;
import zw.com.organicare.repository.UserRepository;
import zw.com.organicare.service.ReferringPractitionerService.ReferringPractitionerService;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReferringPractitionerServiceImpl implements ReferringPractitionerService {
    private final UserRepository userRepository;
    private final ReferringPractitionerRespository referringPractitionerRespository;

    @Override
    public ReferringPracDto register(ReferringPracDto referringPracDto) {
        log.info("Processing referring practitioner registration request for userId--------------------------->: {}", referringPracDto.getUserId());
        User referringPractitioner = userRepository.findById(referringPracDto.getUserId())
                .orElseThrow(() -> new UserNotFound("User not found  with name"));

        referringPractitioner.setRole(Role.RP);
        userRepository.save(referringPractitioner);
        log.info("adding referring practitioner details--------------------------->: {}", referringPractitioner.getFullName());
        ReferringPractitioner practitioner = ReferringPractitioner.builder()
                .commission_rate(referringPracDto.getCommission_rate())
                .user(referringPractitioner)
                .build();
log.info("practitioner details--------------------------->: {}", practitioner);
        referringPractitionerRespository.save(practitioner);

        return ReferringPracDto.builder()
                .commission_rate(practitioner.getCommission_rate())
                .userId(referringPractitioner.getUserId())
                .build();
    }

    @Override
    @Transactional
    public ReferringPracDto update(ReferringPracDto request) {
        log.info("Processing referring practitioner update request for rp_Id:------------> {}", request.getUserId());

        ReferringPractitioner practitioner = referringPractitionerRespository.findById(request.getRp_Id())
                .orElseThrow(() -> new UserNotFound("Referring practitioner not found with id: " + request.getRp_Id()));


        if (request.getCommission_rate() != null) {
            practitioner.setCommission_rate(request.getCommission_rate());
        }
        User user = practitioner.getUser();
        if (request.getActive() != null) {
            user.setIsActive(request.getActive());
            userRepository.save(user);
        }

        referringPractitionerRespository.save(practitioner);

        log.info("Updated referring practitioner details for user:-------------------> {}", practitioner.getUser().getFullName());

        return ReferringPracDto.builder()
                .commission_rate(practitioner.getCommission_rate())
                .build();
    }
}
