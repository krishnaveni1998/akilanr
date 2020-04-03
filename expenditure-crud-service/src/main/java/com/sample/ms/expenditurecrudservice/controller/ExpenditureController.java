package com.sample.ms.expenditurecrudservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sample.ms.expenditurecrudservice.dao.Expenditure;
import com.sample.ms.expenditurecrudservice.dao.ExpenditureRepository;
import com.sample.ms.expenditurecrudservice.dto.ExpenditureDTO;
import com.sample.ms.expenditurecrudservice.dto.ExpenditureListDTO;

@RestController
public class ExpenditureController {

	@Autowired
	ExpenditureRepository expenditureRepository;

	Logger logger = LoggerFactory.getLogger("expenditurecrud");

	/**
	 * API for Create and update new expenditure detail
	 * 
	 * @param expenditureReqDto
	 * @return
	 */
	@RequestMapping(value = "/cuexpenditure", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ExpenditureDTO addExpenditure(@RequestBody ExpenditureDTO expenditureDto) {
		logger.info("Start of add expenditure");
		try {
			expenditureDto.setExpenditure(expenditureRepository.save(expenditureDto.getExpenditure()));
			expenditureDto.setResponseCode(0);
			expenditureDto.setResponseStatus("Saved Successfully");
			return expenditureDto;
		} catch (Exception e) {
			logger.debug("Exception Occured", e);
			logger.error("Exception Occured", e);
			expenditureDto.setResponseCode(0);
			expenditureDto.setResponseStatus("Saved failed");
			return expenditureDto;
		} finally {
			logger.info("End of add expenditure");
			logger.debug("Response :: " + expenditureDto.getResponseStatus() + " | response code :: "
					+ expenditureDto.getResponseCode());
		}
	}

	/**
	 * Get all expenture details
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getexpenditure", method = RequestMethod.GET, produces = "application/json")
	public ExpenditureListDTO getExpenditure() {
		logger.info("Start of get expenditure");
		List<Expenditure> expenditures = null;
		ExpenditureListDTO expenditureListDTO = new ExpenditureListDTO();
		try {
			expenditures = expenditureRepository.findAll();
			if (expenditures.size() > 0) {
				expenditureListDTO.setExpenditures(expenditures);
				expenditureListDTO.setResponseCode(0);
				expenditureListDTO.setResponseStatus("Success");
			} else {
				expenditureListDTO.setResponseCode(0);
				expenditureListDTO.setResponseStatus("No Records Found");
			}
			return expenditureListDTO;
		} catch (Exception e) {
			logger.debug("Exception Occured", e);
			logger.error("Exception Occured", e);
			return expenditureListDTO;
		} finally {
			logger.info("End of get expenditure");
			logger.debug("Response :: " + expenditureListDTO.getResponseStatus() + " | response code :: "
					+ expenditureListDTO.getResponseCode());
		}
	}

	/**
	 * Get Expenditure details using id
	 * 
	 * @param expenditureId
	 * @return
	 */
	@RequestMapping(value = "/getexpenditure/{expenditureId}", method = RequestMethod.GET, produces = "application/json")
	public ExpenditureDTO getExpenditureById(@PathVariable int expenditureId) {
		logger.info("Start of get expenditure by id");
		logger.debug("Start of get expenditure by id" + expenditureId);
		Expenditure expenditure = null;
		ExpenditureDTO expenditureDTO = new ExpenditureDTO();
		try {
			expenditure = expenditureRepository.findByExpenditureIdAndDeleteStatus(expenditureId, false);
			expenditureDTO.setExpenditure(expenditure);
			if (expenditure != null && expenditure.getExpenditureId() > 0) {
				expenditureDTO.setResponseCode(0);
				expenditureDTO.setResponseStatus("Success");
			} else {
				expenditure = new Expenditure();
				expenditure.setExpenditureId(expenditureId);
				expenditureDTO.setExpenditure(expenditure);
				expenditureDTO.setResponseCode(0);
				expenditureDTO.setResponseStatus("No Record Found");
			}
			return expenditureDTO;
		} catch (Exception e) {
			logger.debug("Exception Occured", e);
			logger.error("Exception Occured", e);
			expenditureDTO.setResponseCode(1);
			expenditureDTO.setResponseStatus("falied");
			return expenditureDTO;
		} finally {
			logger.info("End of get expenditure by id");
			logger.debug("Response :: " + expenditureDTO.getResponseStatus() + " | response code :: "
					+ expenditureDTO.getResponseCode());
		}
	}

	/**
	 * Delete Expenditure by Id (Soft Delete mentioned in the column DeleteStatus)
	 * 
	 * @param expenditureId
	 * @return
	 */
	@RequestMapping(value = "/deleteexpenditure/{expenditureId}", method = RequestMethod.POST, produces = "application/json")
	public ExpenditureDTO deleteExpenditure(@PathVariable int expenditureId) {
		logger.info("start of delete expenditure by id");
		logger.debug("start of delete expenditure by id " + expenditureId);
		ExpenditureDTO expenditureDTO = new ExpenditureDTO();
		try {
			Expenditure expenditure = expenditureRepository.findByExpenditureIdAndDeleteStatus(expenditureId, false);
			if (expenditure != null && expenditure.getExpenditureId() > 0) {
				expenditure.setDeleteStatus(true);
				expenditure = expenditureRepository.save(expenditure);
				expenditureDTO.setExpenditure(expenditure);
				expenditureDTO.setResponseCode(0);
				expenditureDTO.setResponseStatus("Deleted Successfully");
			} else {
				expenditure = new Expenditure();
				expenditure.setExpenditureId(expenditureId);
				expenditureDTO.setExpenditure(expenditure);
				expenditureDTO.setResponseCode(0);
				expenditureDTO.setResponseStatus("Deleted Successfully");
			}
			return expenditureDTO;
		} catch (Exception e) {
			logger.debug("Exception Occured", e);
			logger.error("Exception Occured", e);
			expenditureDTO.setResponseCode(0);
			expenditureDTO.setResponseStatus("Deletion failed");
			return expenditureDTO;
		} finally {
			logger.info("End of delete expenditure by id");
			logger.debug("Response :: " + expenditureDTO.getResponseStatus() + " | response code :: "
					+ expenditureDTO.getResponseCode());
		}
	}

}
