package com.ldy.service;

public interface UserRedPacketService {
    /**
     * 保存抢红包信息
     * @param redPacketId 红包编号
     * @param userId 抢红包用户编号
     * @return 影响记录条数
     */
    public int grabRedPacket(Long redPacketId, Long userId);

    /**
     * 通过乐观锁与版本号机制抢红包
     * @param redPacketId 红包编号
     * @param userId 抢红包用户编号
     * @return 影响记录条数
     */
    public int grabRedPacketForVersion(Long redPacketId, Long userId);
}
