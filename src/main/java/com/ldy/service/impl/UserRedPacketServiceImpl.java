package com.ldy.service.impl;

import com.ldy.dao.RedPacketDao;
import com.ldy.dao.UserRedPacketDao;
import com.ldy.pojo.RedPacket;
import com.ldy.pojo.UserRedPacket;
import com.ldy.service.UserRedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRedPacketServiceImpl implements UserRedPacketService {
    @Autowired
    private UserRedPacketDao userRedPacketDao = null;

    @Autowired
    private RedPacketDao redPacketDao = null;

    private static final int FAILED = 0;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int grabRedPacket(Long redPacketId, Long userId) {
        //获取红包信息
        //RedPacket redPacket = redPacketDao.getRedPacket(redPacketId);
        RedPacket redPacket = redPacketDao.getRedPacketForUpdate(redPacketId);
        //当前小红包库存大于0
        if (redPacket.getStock() > 0) {
            redPacketDao.decreaseRedPacket(redPacketId);
            //生成抢红包信息
            UserRedPacket userRedPacket = new UserRedPacket();
            userRedPacket.setRedPacketId(redPacketId);
            userRedPacket.setUserId(userId);
            userRedPacket.setAmount(redPacket.getUnitAmount());
            userRedPacket.setNote("抢红包 " + redPacketId);
            //插入抢红包信息
            int result = userRedPacketDao.grabRedPacket(userRedPacket);
            return result;
        }
        //失败返回
        return FAILED;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int grabRedPacketForVersion(Long redPacketId, Long userId) {
        //获取红包信息，注意version值
        RedPacket redPacket = redPacketDao.getRedPacket(redPacketId);
        //当前小红包库存大于0
        if (redPacket.getStock() > 0) {
            //再次线程保存的version旧值给SQL判断，是否有其他线程修改过数据
            int update = redPacketDao.decreaseRedPacketForVersion(redPacketId, redPacket.getVersion());
            //如果没有数据更新，则说明其他线程已经修改过数据，本次抢红包失败
            if (update == 0) {
                return FAILED;
            }
            //生成抢红包信息
            UserRedPacket userRedPacket = new UserRedPacket();
            userRedPacket.setRedPacketId(redPacketId);
            userRedPacket.setUserId(userId);
            userRedPacket.setAmount(redPacket.getUnitAmount());
            userRedPacket.setNote("抢红包 " + redPacketId);
            //插入抢红包信息
            int result = userRedPacketDao.grabRedPacket(userRedPacket);
            return result;
        }
        //失败返回
        return FAILED;
    }
}
