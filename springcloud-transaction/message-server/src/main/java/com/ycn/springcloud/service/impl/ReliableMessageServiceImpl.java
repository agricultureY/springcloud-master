package com.ycn.springcloud.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ycn.springcloud.entity.ReliableMessage;
import com.ycn.springcloud.mapper.ReliableMessageMapper;
import com.ycn.springcloud.service.IReliableMessageService;
import org.springframework.stereotype.Service;

/**
 * @author ycn
 * @package com.ycn.springcloud.service
 * @ClassName IReliableMessageServiceImpl
 * @Date 2018/7/30 9:40
 */
@Service
public class ReliableMessageServiceImpl extends ServiceImpl<ReliableMessageMapper, ReliableMessage>
        implements IReliableMessageService {

}
