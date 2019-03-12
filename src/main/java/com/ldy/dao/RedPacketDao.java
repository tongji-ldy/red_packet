package com.ldy.dao;

import com.ldy.pojo.RedPacket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RedPacketDao {
    String TABLE_NAME = " t_red_packet ";
    String INSERT_FIELDS = " user_id, amount, send_date, total, unit_amount, stock, version, note ";//前后加空格，以免拼接时出错
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    /**
     * 获取红包信息
     * @param id 红包id
     * @return 红包具体信息
     */
    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where id = #{id}"})
    public RedPacket getRedPacket(Long id);

    /**
     * 扣减抢红包数
     * @param id 红包id
     * @return 更新记录条数
     */
    @Update({"update", TABLE_NAME, "set stock = stock - 1 where id = #{id}"})
    public int decreaseRedPacket(Long id);

    /**
     * 使用for update语句加锁
     * @param id 红包id
     * @return 红包信息
     */
//    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where id = #{id} for update"})
//    public RedPacket getRedPacketForUpdate(Long id);
}
