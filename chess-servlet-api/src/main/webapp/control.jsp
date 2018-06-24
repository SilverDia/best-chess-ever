<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Buchungsjournal</title>
    </head>
    <body>
    
    <div id="div-head" class="div-head text">
    	<h1>Herzlich Willkommen im Buchungsjournal</h1>
        <h3>Bitte wählen Sie das Jahr, welches Sie bearbeiten möchten.</h3>
    </div>
    <div id="div-body" class="div-body">
    	<div id="div-body-text" class="div-body text">
    	
    	</div>
    	<div id="div-body-table" class="div-body table">
    		<table>
    			<tbody>
    				<tr>
    					<td id="td-new-year" class="submit td">
    						<form id="form-new-year" action="CreateNewYearServlet" method="get">
    							<input type="submit" id="input-submit-new-year" name="input-submit-new-year">
    						</form>
    					</td>
    					<td id="td-year-select" class="select td">
    					
    					</td>
    					<td id="td-ok" class="submit td">    					    						
    						<form id="form-new-year" action="CreateNewYearServlet" method="get">
    							<input type="submit" id="input-submit-ok" name="input-submit-ok">
    						</form>
    					</td>
    				</tr>
    			</tbody>
    		</table>
    	</div>
    <div id="div-foot" class="div-foot">
    	<p align="right">© Lea Werner</p>
    </div>
        
    </body>
</html>
