/**
 * @author : tadiewa
 * date: 9/11/2025
 */

package zw.com.organicare.service.ReferringPractitionerService;

import zw.com.organicare.dto.referringPrac.ReferringPracDto;

public interface ReferringPractitionerService {

    ReferringPracDto register(ReferringPracDto referringPracDto);
    ReferringPracDto update(ReferringPracDto referringPracDto);
}
