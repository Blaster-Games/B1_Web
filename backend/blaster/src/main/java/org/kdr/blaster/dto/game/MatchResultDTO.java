package org.kdr.blaster.dto.game;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class MatchResultDTO {
    private String mapName;
    private String gameMode;
    private List<BuffInfoDTO> buffPurchases;
    private List<ThrowableInfoDTO> throwablePurchases;
    private List<WeaponInfoDTO> weaponPurchases;
}
