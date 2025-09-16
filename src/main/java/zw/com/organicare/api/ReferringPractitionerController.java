/**
 * @author : tadiewa
 * date: 9/11/2025
 */


package zw.com.organicare.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.com.organicare.dto.referringPrac.ReferringPracDto;
import zw.com.organicare.service.ReferringPractitionerService.ReferringPractitionerService;

@RestController
@RequestMapping("/api/agent/")
@Slf4j
@RequiredArgsConstructor
public class ReferringPractitionerController {
    private final ReferringPractitionerService referringPractitionerService;


    @PostMapping("register")
    ResponseEntity<ReferringPracDto> register(@RequestBody ReferringPracDto referringPracDto){
        ReferringPracDto response = referringPractitionerService.register(referringPracDto);
        log.info("Referring practitioner --------------------------->: {}", response);
        return ResponseEntity.ok(response);
    }

    @PutMapping("update")
    ResponseEntity<ReferringPracDto> update(@RequestBody ReferringPracDto referringPracDto){
        ReferringPracDto response = referringPractitionerService.update(referringPracDto);
        log.info("Referring practitioner --------------------------->: {}", response);
        return ResponseEntity.ok(response);
    }
}
