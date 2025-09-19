/**
 * @author : tadiewa
 * date: 9/19/2025
 */


package zw.com.organicare.service.change.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.com.organicare.dto.change.UncollectedChangeResponse;
import zw.com.organicare.exception.ResourceNotFoundException;
import zw.com.organicare.exception.UserNotFound;
import zw.com.organicare.model.Sale;
import zw.com.organicare.model.UncollectedChange;
import zw.com.organicare.model.User;
import zw.com.organicare.repository.SaleRepository;
import zw.com.organicare.repository.UncollectedChangeRepository;
import zw.com.organicare.repository.UserRepository;
import zw.com.organicare.security.JwtService;
import zw.com.organicare.service.authService.AuthService;
import zw.com.organicare.service.change.UncollectedChangeService;
import zw.com.organicare.utils.UncollectedChangeMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class UncollectedChangeServiceImpl implements UncollectedChangeService {

    private final UncollectedChangeRepository uncollectedChangeRepository;
    private final AuthService authService;
    private final SaleRepository saleRepository;

    @Transactional
    @Override
    public UncollectedChangeResponse collectChange(Long uncollectedChangeId) {

           var currentUser =authService.getAuthenticatedUser();
           log.info("Current user collecting change: {}", currentUser.getFullName());


        UncollectedChange uc = uncollectedChangeRepository.findById(uncollectedChangeId)
                .orElseThrow(() -> new ResourceNotFoundException("UncollectedChange not found"));

        if (uc.isResolved()) {
            throw new IllegalStateException("Change already collected");
        }
        uc.setResolved(true);
        uc.setNotes("Change collected on " + LocalDateTime.now());
        uc.setGivenBy(currentUser.getFullName());
        uncollectedChangeRepository.save(uc);

        Sale sale = saleRepository.findById(uc.getSale().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Associated sale not found"));
        sale.setChangeGiven(uc.getChangeAmount());
        sale.setUncollectedChange(BigDecimal.ZERO);
        saleRepository.save(sale);

        return UncollectedChangeMapper.toDto(uc);
    }

    @Override
    public List<UncollectedChangeResponse> getPendingChangesForPatient(Long patientId) {
        return uncollectedChangeRepository.findByPatient_PatientIdAndResolvedFalse(patientId)
                .stream()
                .map(UncollectedChangeMapper::toDto)
                .toList();
    }
}


