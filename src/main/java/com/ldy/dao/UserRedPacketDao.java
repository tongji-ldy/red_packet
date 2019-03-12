package com.ldy.dao;

import com.ldy.pojo.UserRedPacket;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserRedPacketDao {
    String TABLE_NAME = " t_user_red_packet ";
    String INSERT_FIELDS = " red_packet_id, user_id, amount, grab_time, note ";//前后加空格，以免拼接时出错
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    /**
     * 插入抢红包信息
     * @param userRedPacket 抢红包信息
     * @return 影响记录数
     */
    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{redPacketId},#{userId},#{amount},now(),#{note})"})
    public int grabRedPacket(UserRedPacket userRedPacket);
}
