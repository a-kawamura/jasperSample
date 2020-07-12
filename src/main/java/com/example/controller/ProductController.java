package com.example.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.dao.SampleProductDao;
import com.example.model.SampleProductModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
public class ProductController {

	@Autowired
	ResourceLoader resouceLoader;

	private static final Logger logger = LoggerFactory
			.getLogger(ProductController.class);

	@RequestMapping(path = "/sample", method = RequestMethod.GET)
	public ResponseEntity<byte[]> ample(HttpServletResponse response) {

		// データ作成
		Map<String, Object> params = new HashMap<>();

		// ヘッダーデータ作成
		params.put("clientName", "山本証券");
		JapaneseDate now = JapaneseDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("GGGGy年M月d日",
				new Locale("ja", "JP", "JP"));
		params.put("dateToday", now.format(formatter));

		SampleProductDao dao = new SampleProductDao();
		List<SampleProductModel> fields = dao.findByAll();

		try {
			// to download pdf file
//			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
//					.header("Content-disposition",
//							"attachment; filename=sample.pdf")
//					.body(reporting(params, fields));

			// To open pdf on browser
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
					.header("Content-disposition",
							"inline; filename=sample.pdf")
					.body(reporting(params, fields));
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e); // Havo to change appropriate
											// RuntimeException
		}
	}

	private byte[] reporting(Map<String, Object> params,
			List<SampleProductModel> data) throws IOException, JRException {

		try (InputStream input = new FileInputStream(
				resouceLoader.getResource("classpath:report/jasperSample.jrxml")
						.getFile())) {

			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
					data);
			JasperReport jasperReport = JasperCompileManager
					.compileReport(input);

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
					params, dataSource);

			return JasperExportManager.exportReportToPdf(jasperPrint);
		}
	}
}
