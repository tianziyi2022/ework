package com.hebutgo.ework.service;

import com.hebutgo.ework.entity.WorkSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hebutgo.ework.entity.request.*;
import com.hebutgo.ework.entity.vo.SubmitWorkVo;
import com.hebutgo.ework.entity.vo.WorkDetailVo;

import java.util.List;

/**
 * <p>
 * 提交作业内容表 服务类
 * </p>
 *
 * @author tianziyi
 * @since 2020-08-22
 */
public interface IWorkSubmitService extends IService<WorkSubmit> {
    public SubmitWorkVo complete(CompleteWorkRequest completeWorkRequest);
    public SubmitWorkVo submit(SubmitWorkRequest submitWorkRequest);
    public SubmitWorkVo withdraw(SubmitWorkRequest submitWorkRequest);
    public SubmitWorkVo returnWork(ReturnWorkRequest returnWorkRequest);
    public SubmitWorkVo correct(CorrectWorkRequest correctWorkRequest);
    public SubmitWorkVo recorrect(CorrectWorkRequest correctWorkRequest);
    public WorkDetailVo detail(WorkDetailRequest workDetailRequest);
    public List<WorkDetailVo> detailList(WorkDetailListRequest workDetailListRequest);
}
