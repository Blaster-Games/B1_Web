package org.kdr.blaster.dto.game;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class WeaponInfoDTO {
    private String weaponType;
    private int purchaseCount;
}
