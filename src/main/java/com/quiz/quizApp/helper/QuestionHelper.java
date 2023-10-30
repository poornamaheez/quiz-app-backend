package com.quiz.quizApp.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.quiz.quizApp.model.Question;

public class QuestionHelper {
	  public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";	  
	  
	  public static boolean hasExcelFormat(MultipartFile questionFile) {
		
		  if (!(TYPE.equals(questionFile.getContentType()))) {
			return false;
		}
		  return true;
	}
	  public static List<Question> excelToQuestions(InputStream is, MultipartFile questionFile) {
		try {
			
			Workbook workbook = new XSSFWorkbook(is);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rows = sheet.rowIterator();
			
			List<Question> questions = new ArrayList<Question>();
			Row headerRow = sheet.getRow(0);
			
			List<String> headerNames = new ArrayList<String>();
		       Map<String, String> errorMap = new HashMap<>();

		    Map<String, String> headerMap = new HashMap<>();
		    
		    headerMap.put("questionnumber", "Question Number");
		    headerMap.put("question", "Question");
		    headerMap.put("optiona","Option A");
		    headerMap.put("optionb", "Option B");
		    headerMap.put("optionc", "Option C");
		    headerMap.put("optiond", "Option D");
		    headerMap.put("answer", "Answer");
		    List<String> mandatoryHeaderList = new ArrayList<>(headerMap.keySet());

		       

	        for (Cell cell : headerRow) {
	            String cellValue = cell.getStringCellValue().replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
	            if(headerNames.contains(cellValue)) {
	            	errorMap.put("HeaderRepeatError", "Header name repeated: "+headerMap.get(cellValue)+" in ["+questionFile.getOriginalFilename()+"]");
	            	workbook.close();
		            throw new RuntimeException(errorMap.toString());
	            }
	            headerNames.add(cellValue);
	        }
	        List<String> notFoundList = new ArrayList<>();
	        for (String str : mandatoryHeaderList) {
	            if (!headerNames.contains(str)) {
	                notFoundList.add(headerMap.get(str));
	            }
	        }



	       if (!headerNames.containsAll(mandatoryHeaderList)) {
	    	   if(notFoundList.size()==1) {
		    	   	
	    		  errorMap.put("Column Name",notFoundList.toString()+" is missing in ["+questionFile.getOriginalFilename()+"] Please look into help section");

	    	   }
	    	   else {
		    	   	errorMap.put("Column Names",notFoundList.toString()+" are missing in ["+questionFile.getOriginalFilename()+"] Please look into help section");

	    	   }
            	workbook.close();
	            throw new RuntimeException(errorMap.toString());

	       }
	       
	       int rowNumber = 0;
	       
	       while (rows.hasNext()) {
		        Row currentRow = rows.next();

		        if (rowNumber ==0 ) {
		          rowNumber++;
		          continue;
		        }
		    Question question = new Question();
		    
		    for(String head:headerNames) {
	            Cell currentCell = currentRow.getCell(headerNames.indexOf(head));
	          switch (head) {
	          case "questionnumber":
	        	  try {
	        		if (currentCell != null) {
	                  question.setQuestionId((long) currentCell.getNumericCellValue());
	              } else {
	                  errorMap.put(headerMap.get(head), "Cell is null, must contain a number, at row " + (currentRow.getRowNum()+1) + " in [" + questionFile.getOriginalFilename() + "]");
	              }}
	        	  catch (IllegalStateException | NumberFormatException e) {
	        		  errorMap.put(headerMap.get(head), "Invalid value, must be a number, at row "+(currentRow.getRowNum()+1)+" in ["+questionFile.getOriginalFilename()+"]");
	        	  }
	        	  break;
	          case "question":
	        		if(currentCell==null|| currentCell.getCellType() == CellType.BLANK) {
	        			errorMap.put(headerMap.get(head), "Question should be present, at row "+(currentRow.getRowNum()+1));
	        		}else if(currentCell.getStringCellValue().isBlank()) {
	        			errorMap.put(headerMap.get(head), "Question should be present, at row "+(currentRow.getRowNum()+1));
	        		}
	        		else {
						question.setQuestion(currentCell.getStringCellValue().trim());
					}
	        	  break;
	          case "optiona":
	        		if(currentCell==null|| currentCell.getCellType() == CellType.BLANK) {
						errorMap.put(headerMap.get(head), "Option A should be present, at row "+(currentRow.getRowNum()+1));
	        		}else if(currentCell.getStringCellValue().isBlank()) {
	        			errorMap.put(headerMap.get(head), "Option A should be present, at row "+(currentRow.getRowNum()+1));
	        		}else {
						question.setOption_a(currentCell.getStringCellValue().trim());
					}
	        	  break;
	          case "optionb":
	        		if(currentCell==null|| currentCell.getCellType() == CellType.BLANK) {
						errorMap.put(headerMap.get(head), "Option B should be present, at row "+(currentRow.getRowNum()+1));

	        		}else if(currentCell.getStringCellValue().isBlank()) {
	        			errorMap.put(headerMap.get(head), "Option B should be present, at row "+(currentRow.getRowNum()+1));
	        		}else {
	  	        	  question.setOption_b(currentCell.getStringCellValue().trim());

					}
	        	  break;
	          case "optionc":

	        		if(currentCell==null|| currentCell.getCellType() == CellType.BLANK) {
						errorMap.put(headerMap.get(head), "Option C should be present, at row "+(currentRow.getRowNum()+1));

	        		}else if(currentCell.getStringCellValue().isBlank()) {
	        			errorMap.put(headerMap.get(head), "Option C should be present, at row "+(currentRow.getRowNum()+1));
	        		}else {
	  	        	  question.setOption_c(currentCell.getStringCellValue().trim());

	        		}
	        	  break;
	          case "optiond":

	        		if(currentCell==null|| currentCell.getCellType() == CellType.BLANK) {
						errorMap.put(headerMap.get(head), "Option D should be present, at row "+(currentRow.getRowNum()+1));

	        		}else if(currentCell.getStringCellValue().isBlank()) {
	        			errorMap.put(headerMap.get(head), "Option D should be present, at row "+(currentRow.getRowNum()+1));
	        		}else {
	  	        	  question.setOption_d(currentCell.getStringCellValue().trim());

					}
	        	  break;
	          case "answer":

	        		if(currentCell==null|| currentCell.getCellType() == CellType.BLANK) {
						errorMap.put(headerMap.get(head), "Answer should be present, at row "+(currentRow.getRowNum()+1));

	        		}else if(currentCell.getStringCellValue().isBlank()) {
	        			errorMap.put(headerMap.get(head), "Answer should be present, at row "+(currentRow.getRowNum()+1));
	        		}else {
						question.setAnswer(currentCell.getStringCellValue().trim());
					}
	        	  break;
	          default:
	        	  break;
	          }
	          
	          questions.add(question);
		    }
	       }
	       
	       workbook.close();
	       if (!errorMap.isEmpty()) {
	            throw new RuntimeException(errorMap.values().toString());
	        }
	    return questions;
			
		} catch (IOException e) {
		      throw new RuntimeException("fail to parse the question file: " + e.getMessage());
		}
		
	}
}
