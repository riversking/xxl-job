package com.xxl.job.admin.controller;

import com.xxl.job.admin.core.model.XxlJobGroup;
import com.xxl.job.admin.core.util.I18nUtil;
import com.xxl.job.admin.mapper.XxlJobGroupMapper;
import com.xxl.job.admin.mapper.XxlJobInfoMapper;
import com.xxl.job.core.biz.model.ReturnT;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * job group controller
 * @author xuxueli 2016-10-02 20:52:56
 */
@Controller
@RequestMapping("/jobgroup")
public class JobGroupController {

	@Resource
	public XxlJobInfoMapper xxlJobInfoMapper;
	@Resource
	public XxlJobGroupMapper xxlJobGroupMapper;

	@RequestMapping
	public String index(Model model) {

		// job group (executor)
		List<XxlJobGroup> list = xxlJobGroupMapper.findAll();

		model.addAttribute("list", list);
		return "jobgroup/jobgroup.index";
	}

	@RequestMapping("/save")
	@ResponseBody
	public ReturnT<String> save(XxlJobGroup xxlJobGroup){

		// valid
		if (xxlJobGroup.getAppName()==null || StringUtils.isBlank(xxlJobGroup.getAppName())) {
			return new ReturnT<String>(500, (I18nUtil.getString("system_please_input")+"AppName") );
		}
		if (xxlJobGroup.getAppName().length()<4 || xxlJobGroup.getAppName().length()>64) {
			return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_appName_length") );
		}
		if (xxlJobGroup.getTitle()==null || StringUtils.isBlank(xxlJobGroup.getTitle())) {
			return new ReturnT<String>(500, (I18nUtil.getString("system_please_input") + I18nUtil.getString("jobgroup_field_title")) );
		}
		if (xxlJobGroup.getAddressType()!=0) {
			if (StringUtils.isBlank(xxlJobGroup.getAddressList())) {
				return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_addressType_limit") );
			}
			String[] addresss = xxlJobGroup.getAddressList().split(",");
			for (String item: addresss) {
				if (StringUtils.isBlank(item)) {
					return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_registryList_unvalid") );
				}
			}
		}

		int ret = xxlJobGroupMapper.save(xxlJobGroup);
		return (ret>0)?ReturnT.SUCCESS:ReturnT.FAIL;
	}

	@RequestMapping("/update")
	@ResponseBody
	public ReturnT<String> update(XxlJobGroup xxlJobGroup){
		// valid
		if (xxlJobGroup.getAppName()==null || StringUtils.isBlank(xxlJobGroup.getAppName())) {
			return new ReturnT<String>(500, (I18nUtil.getString("system_please_input")+"AppName") );
		}
		if (xxlJobGroup.getAppName().length()<4 || xxlJobGroup.getAppName().length()>64) {
			return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_appName_length") );
		}
		if (xxlJobGroup.getTitle()==null || StringUtils.isBlank(xxlJobGroup.getTitle())) {
			return new ReturnT<String>(500, (I18nUtil.getString("system_please_input") + I18nUtil.getString("jobgroup_field_title")) );
		}
		if (xxlJobGroup.getAddressType()!=0) {
			if (StringUtils.isBlank(xxlJobGroup.getAddressList())) {
				return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_addressType_limit") );
			}
			String[] addresss = xxlJobGroup.getAddressList().split(",");
			for (String item: addresss) {
				if (StringUtils.isBlank(item)) {
					return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_registryList_unvalid") );
				}
			}
		}

		int ret = xxlJobGroupMapper.update(xxlJobGroup);
		return (ret>0)?ReturnT.SUCCESS:ReturnT.FAIL;
	}

	@RequestMapping("/remove")
	@ResponseBody
	public ReturnT<String> remove(int id){

		// valid
		int count = xxlJobInfoMapper.pageListCount(0, 10, id, null, null);
		if (count > 0) {
			return new ReturnT<String>(500, I18nUtil.getString("jobgroup_del_limit_0") );
		}

		List<XxlJobGroup> allList = xxlJobGroupMapper.findAll();
		if (allList.size() == 1) {
			return new ReturnT<String>(500, I18nUtil.getString("jobgroup_del_limit_1") );
		}

		int ret = xxlJobGroupMapper.remove(id);
		return (ret>0)?ReturnT.SUCCESS:ReturnT.FAIL;
	}

}