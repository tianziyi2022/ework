package com.hebutgo.ework.service;

import com.hebutgo.ework.entity.WorkDemand;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hebutgo.ework.entity.request.*;
import com.hebutgo.ework.entity.vo.CreateDemandVo;
import com.hebutgo.ework.entity.vo.DemandDetailVo;

import java.util.List;

/**
 * <p>
 * 作业需求（发布作业内容）表 服务类
 * </p>
 *
 * @author tianziyi
 * @since 2020-08-22
 */
public interface IWorkDemandService extends IService<WorkDemand> {
    public CreateDemandVo create(CreateDemandRequest createDemandRequest);
    public CreateDemandVo change(ChangeDemandRequest changeDemandRequest);
    public CreateDemandVo announce(AnnounceDemandRequest aunounceDemandRequest);
    public CreateDemandVo delete(DeleteDemandRequest deleteDemandRequest);
    public CreateDemandVo withdraw(DeleteDemandRequest deleteDemandRequest);
    public DemandDetailVo detail(DemandDetailRequest demandDetailRequest);
    public List<DemandDetailVo> detailList(DemandDetailListRequest demandDetailListRequest);
}
