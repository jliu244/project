package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import connectDB.*;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import connectDB.connectDB;
import function.SortGrade;

import java.sql.Statement;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import object.Assessment;
import object.AssessmentResult;
import object.Course;
import object.Question;
import object.Rubric;
import object.RubricItem;
import object.SearchResultMain;
import object.Student;
import javafx.scene.control.Alert; 
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class mainController implements Initializable {
	
	private static float forAssessmentRemainingWeight;
	public static float getForAssessmentRemainingWeight() {
		return forAssessmentRemainingWeight;
	}
	private static String forAssessmentTitle;
	public static String getForAssessmentTitle() {
		return forAssessmentTitle;
	}
	private static String forAssessmentID;
	public static String getForAssessmentID() {
		return forAssessmentID;
	}
	
	private static String assessmentIDforQuestion;
	public static String getAssessmentIDforQuestion() {
		return assessmentIDforQuestion;
	}
	
	private static String chosenQuestionID;
	public static String getChosenQuestionID() {
		return chosenQuestionID;
	}
	
	private static String courseIDforAssessment;
	public static String getCourseIDforAssessment() {
		return courseIDforAssessment;
	}
	
	private static String selectedCourseID;
	public static String getSelectedCourseID() {
		return selectedCourseID;
	}
	
	List<String> list1 = new ArrayList<String>();
	List<String> list2 = new ArrayList<String>();
	
	//this is for analysis export
	private static Course courseIDChoosen;
	public static Course getCourseIDChoosen() {
		return courseIDChoosen;
	}
	
	
	private static String courseIDforRubric;
	public static String getCourseIDforRubric() {
		return courseIDforRubric;
	}
	
	@FXML
	private TextField CourseTitle;
	@FXML
	private Label ResultDisplay;

	ObservableList<RubricItem> rubricItem = FXCollections.observableArrayList();
	@FXML
	private TableView<RubricItem> rubricItemTable = new TableView<RubricItem>(rubricItem);
	@FXML
	private TableColumn<RubricItem, String> rubricTitle;
	@FXML
	private TableColumn<RubricItem, Float> rubricPercentage;
	@FXML
	private TableColumn<RubricItem, Grade> gradeItemAction;
	
	ObservableList<AssessmentResult> assessmentResults = FXCollections.observableArrayList();
	@FXML
	private TableView<AssessmentResult> assessmentResultList = new TableView<AssessmentResult>(assessmentResults);
	@FXML
	private TableColumn<AssessmentResult, String> assessmentTitle;
	@FXML
	private TableColumn<AssessmentResult, String> assessmentWeight;
	@FXML
	private TableColumn<AssessmentResult, String> assessmentGrade;
	
	// For RubricTab rubricItem Table
	ObservableList<RubricItem> rubricItem2 = FXCollections.observableArrayList();
	@FXML
	private TableView<RubricItem> rubricItemTable2 = new TableView<RubricItem>(rubricItem2);
	@FXML
	private TableColumn<RubricItem, Integer> rubricNum;
	@FXML
	private TableColumn<RubricItem, String> rubricTitle2;
	@FXML
	private TableColumn<RubricItem, Float> rubricPercentage2;
	
	
	ObservableList<Student> student = FXCollections.observableArrayList();
	@FXML
	private ListView<Student> studentList4 = new ListView<Student>(student);
	@FXML
	private ListView<Student> studentList3 = new ListView<Student>(student);
	@FXML
	private ListView<Student> studentList1 = new ListView<Student>(student);
	
	ObservableList<Course> course = FXCollections.observableArrayList ();
	@FXML
	ListView<Course> courseList1 = new ListView<Course>(course);	
	@FXML
	ListView<Course> courseList2 = new ListView<Course>(course);	
	@FXML
	ListView<Course> courseList3 = new ListView<Course>(course);	
	@FXML
	ListView<Course> courseList4 = new ListView<Course>(course);	
	
	ObservableList<Question> question = FXCollections.observableArrayList ();
	ObservableList<Assessment> assessment = FXCollections.observableArrayList ();	
	ObservableList<Rubric> rubrics = FXCollections.observableArrayList();
	
	
	@FXML
	ListView<Question> questionList = new ListView<Question>(question);
	@FXML
	ListView<Assessment> assessmentList = new ListView<Assessment>(assessment);
	@FXML
	ListView<Rubric> rubricList = new ListView<Rubric>(rubrics);
	
	@FXML
	private TextField SearchInput;
	
	ObservableList<SearchResultMain> searchResult = FXCollections.observableArrayList();
	@FXML
	ListView<SearchResultMain> searchResultList = new ListView<SearchResultMain>(searchResult);
	
	
	@FXML
	ComboBox<String> chooseSearchList = new ComboBox<String>();
	
	
	public static enum Grade{
		A, B, C, D, F;
		public String toString() {
			return super.toString();
		}
	}
	
	public static class RadioButtonCell<S, T extends Enum<T>> extends TableCell<S, T> {
		private EnumSet<T> enumeration;
		public RadioButtonCell(EnumSet<T> enumeration) {
	        this.enumeration = enumeration;
	    }
		@Override
	    protected void updateItem(T item, boolean empty)
	    {
	        super.updateItem(item, empty);
	        if (!empty) 
	        {
	            // gui setup
	            HBox hb = new HBox(7);
	            hb.setAlignment(Pos.CENTER);
	            final ToggleGroup group = new ToggleGroup();

	            // create a radio button for each 'element' of the enumeration
	            for (Enum<T> enumElement : enumeration) {
	                RadioButton radioButton = new RadioButton(enumElement.toString());
	                radioButton.setUserData(enumElement);
	                radioButton.setToggleGroup(group);
	                hb.getChildren().add(radioButton);
	                if (enumElement.equals(item)) {
	                    radioButton.setSelected(true);
	                }
	            }

	            // issue events on change of the selected radio button
	            group.selectedToggleProperty().addListener((ChangeListener<? super Toggle>) new ChangeListener<Toggle>() {

	                @SuppressWarnings("unchecked")

	                public void changed(ObservableValue<? extends Toggle> observable,
	                        Toggle oldValue, Toggle newValue) {
	                    getTableView().edit(getIndex(), getTableColumn());
	                    RadioButtonCell.this.commitEdit((T) newValue.getUserData());
	                }
	            });
	            setGraphic(hb);
	        } else {
	        	setGraphic(null);
	        }
	    }
	}
	
	@FXML
	public void courseChosen() {
		Course courseChosen = courseList1.getSelectionModel().getSelectedItem();
		String currentCourseID = courseChosen.getCourseID();
		courseIDforAssessment = currentCourseID;
		// TODO Auto-generated method stub
		assessment.clear();
		question.clear();
		student.clear();
		rubricItem.clear();
		try {	    	
	    	Statement stmt = connectDB.connectSqlite().createStatement();
	    	String sql="select * from assessment where courseID = '" + currentCourseID + "'";
	    	ResultSet rs=stmt.executeQuery(sql);
	    	while(rs.next()){
	    		assessment.add(new Assessment(rs.getString(1), rs.getString(2), rs.getFloat(3), rs.getString(4), rs.getString(5)));
	    		list2.add(rs.getString(1));
			}
		} catch(Exception e) {
			// TODO: handle exception
		}
		assessmentList.setItems(assessment);
	}
	
	private static float currentAssessmentWeight;
	@FXML
	public void assessmentChosen() {
		Assessment assessmentChosen = assessmentList.getSelectionModel().getSelectedItem();
		String currentAssessmentID = assessmentChosen.getAssessID();
		assessmentIDforQuestion = currentAssessmentID;
		// TODO Auto-generated method stub
		question.clear();
		student.clear();
		rubricItem.clear();
		try {	    	
	    	Statement stmt = connectDB.connectSqlite().createStatement();
	    	String sql="select * from question where assessmentID = '" + currentAssessmentID + "'";
	    	ResultSet rs=stmt.executeQuery(sql);
	    	while(rs.next()){
	    		question.add(new Question(rs.getString(1), rs.getInt(2), rs.getFloat(3), rs.getString(4), rs.getString(5)));
			}
		} catch(Exception e) {
			// TODO: handle exception
		}
		questionList.setItems(question);
		try {	    	
	    	Statement stmt = connectDB.connectSqlite().createStatement();
	    	String sql="select * from assessment where courseID = '" + courseIDforAssessment + "'";
	    	ResultSet rs=stmt.executeQuery(sql);
	    	while(rs.next()){
	    		currentAssessmentWeight = rs.getFloat(3);
			}
		} catch(Exception e) {
			// TODO: handle exception
		}
	}
	
	@FXML
	public void questionChosen() {
		Question questionChosen = questionList.getSelectionModel().getSelectedItem();
		String currentQuestionID = questionChosen.getQuestionID();
		chosenQuestionID = currentQuestionID;
		student.clear();
		rubricItem.clear();
		try {
			Statement stmt = connectDB.connectSqlite().createStatement();
			String sql = "Select * from student where courseID ='" + courseIDforAssessment + "'";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				student.add(new Student (rs.getString(1), rs.getString(2)));
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getStackTrace());
		}
		studentList1.setItems(student);
	}
	
	private static float currentQuestionWeight;
	private static float grade2;
	private static float grade3;
	@FXML
	public void studentChosen() {
		rubricItem.clear();
		Student studentChosen = studentList1.getSelectionModel().getSelectedItem();
		String currentStudentID = studentChosen.getStudentID();
		float check = 0;
		try {			    	
			Statement stmt = connectDB.connectSqlite().createStatement();
			String sqlRefresh="select * from question where assessmentID = '" + assessmentIDforQuestion + "'";
			ResultSet rs=stmt.executeQuery(sqlRefresh);
			while(rs.next()){
				check = check + rs.getFloat(3);
			}
		} catch(Exception e) {
			// TODO: handle exception
		}		
		if((int)check!=100) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("The total weight of all the questions is currently not 100% !!!");
			alert.setContentText("Please modify the weight of other questions before grading !!!");
			ButtonType buttonTypeOK = new ButtonType("OK");
			alert.getButtonTypes().setAll(buttonTypeOK);
			alert.showAndWait();
		}
		else {
			String checkRubric = "0";
			try {			    	
				Statement stmt = connectDB.connectSqlite().createStatement();
				String sqlRefresh="select * from question where ID = '" + chosenQuestionID + "'";
				ResultSet rs=stmt.executeQuery(sqlRefresh);
				while(rs.next()){
					currentQuestionWeight = rs.getFloat(3);
					checkRubric = rs.getString(4);
				}
			} catch(Exception e) {
				// TODO: handle exception
			}				
			
				gradeItemAction.setCellFactory((param) -> new RadioButtonCell<RubricItem, Grade>(EnumSet.allOf(Grade.class)));
				gradeItemAction.setCellValueFactory(new PropertyValueFactory<RubricItem, Grade>("Grade"));
				gradeItemAction.setOnEditCommit((EventHandler<CellEditEvent<RubricItem, Grade>>) new EventHandler<TableColumn.CellEditEvent<RubricItem,Grade>>() {
					
					
					@Override
					public void handle(CellEditEvent<RubricItem, Grade> event) {
						list1.clear();
						String itemTitle = event.getTableView().getItems().get(event.getTablePosition().getRow()).getItemName();
						float itemPercentage = event.getTableView().getItems().get(event.getTablePosition().getRow()).getItemPer();
						Grade letterGrade = event.getNewValue();
						float itemGrade = 0;
						if(letterGrade.toString().equals("A")) {
							itemGrade = 100;
						}
						else if(letterGrade.toString().equals("B")) {
							itemGrade = 75;
						}
						else if(letterGrade.toString().equals("C")) {
							itemGrade = 50;
						}
						else if(letterGrade.toString().equals("D")) {
							itemGrade = 25;
						}
						else {
							itemGrade = 0;
						}
						float grade1 = itemGrade * (itemPercentage / 100) * (currentQuestionWeight / 100);
						try {	    	
					    	Statement stmt = connectDB.connectSqlite().createStatement();
					    	String sql="update question_student set grade =  '" + grade1 + "' where studentID = '" + currentStudentID + "' and questionID = '" + chosenQuestionID + "' and item = '" + itemTitle + "'";
					    	ResultSet rs=stmt.executeQuery(sql);
						} catch(Exception e) {
							// TODO: handle exception
						}
						try {			    	
							Statement stmt = connectDB.connectSqlite().createStatement();
							String sqlRefresh="select * from question where assessmentID = '" + assessmentIDforQuestion + "'";
							ResultSet rs=stmt.executeQuery(sqlRefresh);
							while(rs.next()){
								list1.add(rs.getString(1));
							}
						} catch(Exception e) {
							// TODO: handle exception
						}							
						grade2 = 0;
						for (Object o : list1) {
							try {
								Statement stmt = connectDB.connectSqlite().createStatement();
								String sqlRefresh = "select * from question_student where studentID = '" + currentStudentID + "' and questionID = '" + o.toString() + "'";		
								ResultSet rs=stmt.executeQuery(sqlRefresh);
								while(rs.next()) {
									grade2 = grade2 + rs.getFloat(4);
								}
							} catch (SQLException e) {
								// TODO: handle exception
								e.printStackTrace();
							}
						}
						try {	    	
					    	Statement stmt = connectDB.connectSqlite().createStatement();
					    	String sql="update assessment_student set grade =  '" + grade2 * (currentAssessmentWeight / 100) + "' where studentID = '" + currentStudentID + "' and assessmentID = '" + assessmentIDforQuestion + "'";
					    	ResultSet rs=stmt.executeQuery(sql);
						} catch(Exception e) {
							// TODO: handle exception
						}						
						grade3 = 0;
						for (Object o : list2) {
							try {
								Statement stmt = connectDB.connectSqlite().createStatement();
								String sqlRefresh = "select * from assessment_student where studentID = '" + currentStudentID + "' and assessmentID = '" + o.toString() + "'";		
								ResultSet rs=stmt.executeQuery(sqlRefresh);
								while(rs.next()) {
									grade3 = grade3 + rs.getFloat(4);
								}
							} catch (SQLException e) {
								// TODO: handle exception
								e.printStackTrace();
							}
						}
						try {			    	
							Statement stmt = connectDB.connectSqlite().createStatement();
							String sqlRefresh="update grade set FinalGrade = '" + grade3 + "' where studentID = '" + currentStudentID + "' and courseID = '" + courseIDforAssessment + "'";
							ResultSet rs=stmt.executeQuery(sqlRefresh);
						} catch(Exception e) {
							// TODO: handle exception
						}		
					}
					
					
				});
				rubricTitle.setCellValueFactory(new PropertyValueFactory<RubricItem, String>("ItemName"));
				rubricPercentage.setCellValueFactory(new PropertyValueFactory<RubricItem, Float>("ItemPer"));
				try {	    	
			    	Statement stmt = connectDB.connectSqlite().createStatement();
			    	String sql="select * from item where rubricID = '" + checkRubric + "'";
			    	ResultSet rs=stmt.executeQuery(sql);
			    	while(rs.next()){
			    		rubricItem.add(new RubricItem(rs.getString(2), rs.getFloat(3)));
					}
				} catch(Exception e) {
					// TODO: handle exception
				}
				rubricItemTable.setItems(rubricItem);
				//rubricItemTable.getColumns().addAll(rubricTitle, rubricPercentage, gradeItemAction);
			
		}
	}
	
	@FXML
	public void courseChosenR() {
		Course courseChosen = courseList2.getSelectionModel().getSelectedItem();
		String currentCourseID = courseChosen.getCourseID();
		courseIDforRubric = currentCourseID;
		// TODO Auto-generated method stub
		rubrics.clear();
		rubricItem2.clear();
		try {
			Statement stmt = connectDB.connectSqlite().createStatement();
			String sql = "Select * from rubric where courseID ='" + currentCourseID + "'";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				rubrics.add(new Rubric(rs.getString(1), rs.getString(2)));
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getStackTrace());
		}
		rubricList.setItems(rubrics);
	}
	
	@FXML
	public void chooseRubric() {
		rubricItem2.clear();
		Rubric currentRubric = rubricList.getSelectionModel().getSelectedItem();
		String currentRubricID = currentRubric.getRubricID();
		// TODO Auto-generated method stub
		try {
			Connection connection = connectDB.connectSqlite();
			Statement stmt = connection.createStatement();
			String sql = "Select * from item where rubricID ='" + currentRubricID + "'";
			ResultSet rSet = stmt.executeQuery(sql);
			int num = 1;
			while (rSet.next()) {
				rubricItem2.add(new RubricItem(rSet.getString(2), rSet.getFloat(3), num));
				num++;
			}
			connection.close();
		}catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
		
		rubricNum.setCellValueFactory(new PropertyValueFactory<RubricItem, Integer>("Index"));
		rubricTitle2.setCellValueFactory(new PropertyValueFactory<RubricItem, String>("ItemName"));
		rubricPercentage2.setCellValueFactory(new PropertyValueFactory<RubricItem, Float>("ItemPer"));
		
		rubricItemTable2.setItems(rubricItem2);
	}
	
	// Event Listener on Button.onAction
	@FXML
	public void modifyQuestion(ActionEvent event) {
		// TODO Autogenerated
		Question chooseQuestion = questionList.getSelectionModel().getSelectedItem();
		
		if(chooseQuestion==null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("You are to modify a question !!!");
			alert.setContentText("Please select a question first !!!");
			ButtonType buttonTypeOK = new ButtonType("OK");
			alert.getButtonTypes().setAll(buttonTypeOK);
			alert.showAndWait();
		}
		else {
			String currentQuestionID = chooseQuestion.getQuestionID();
			chosenQuestionID = currentQuestionID;
			Start.getInstance().assessmentNext();
		}	
	}

	// Event Listener on Button.onAction
	@FXML
	public void editAssessment(ActionEvent event) {
		// TODO Autogenerated
		Assessment chooseAssessment = assessmentList.getSelectionModel().getSelectedItem();
		
		if(chooseAssessment==null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("You are to edit an assessment !!!");
			alert.setContentText("Please select an assessment first !!!");
			ButtonType buttonTypeOK = new ButtonType("OK");
			alert.getButtonTypes().setAll(buttonTypeOK);
			alert.showAndWait();
		}
		else {
			String currentAssessmentID = chooseAssessment.getAssessID();
			String currentAssessmentTitle = chooseAssessment.getAssessTitle();
			float currentAssessmentWeight = chooseAssessment.getAssessWeight();
			float check = 0;
			try {			    	
				Statement stmt = connectDB.connectSqlite().createStatement();
				String sqlRefresh="select * from assessment where courseID = '" + courseIDforAssessment + "'";
				ResultSet rs=stmt.executeQuery(sqlRefresh);
				while(rs.next()){
					check = check + rs.getFloat(3);
				}
			} catch(Exception e) {
				// TODO: handle exception
			}
			forAssessmentID = currentAssessmentID;
			forAssessmentTitle = currentAssessmentTitle;
			forAssessmentRemainingWeight = check - currentAssessmentWeight;
			Start.getInstance().assessmentEdit();
		}
		
	}
	// Event Listener on Button.onAction
	@FXML
	public void newAssessment(ActionEvent event) {
		// TODO Autogenerated
		Course chooseCourse = courseList1.getSelectionModel().getSelectedItem();
		if(chooseCourse==null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("You are to add a new assessment !!!");
			alert.setContentText("Please select a course first !!!");
			ButtonType buttonTypeOK = new ButtonType("OK");
			alert.getButtonTypes().setAll(buttonTypeOK);
			alert.showAndWait();
		}
		else {
			float check = 0;
			try {			    	
				Statement stmt = connectDB.connectSqlite().createStatement();
				String sqlRefresh="select * from assessment where courseID = '" + courseIDforAssessment + "'";
				ResultSet rs=stmt.executeQuery(sqlRefresh);
				while(rs.next()){
					check = check + rs.getFloat(3);
				}
			} catch(Exception e) {
				// TODO: handle exception
			}
			forAssessmentRemainingWeight = check;
			if(check < 100) {
				Start.getInstance().assessmentNew();
			}
			else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText("The total weight of existing assessments have already been 100%, there is no more weight to add new assessment !!!");
				alert.setContentText("Please edit existing assessments to acquire extra weight for new assessments.");
				ButtonType buttonTypeOK = new ButtonType("OK");
				alert.getButtonTypes().setAll(buttonTypeOK);
				alert.showAndWait();
			}
		}
		
	}
	// Event Listener on Button.onAction
	@FXML
	public void deleteAssessment(ActionEvent event) {
		// TODO Autogenerated
		Assessment assessmentChosen = assessmentList.getSelectionModel().getSelectedItem();
		if(assessmentChosen==null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("You are to delete an assessment !!!");
			alert.setContentText("Please select an assessment first !!!");
			ButtonType buttonTypeOK = new ButtonType("OK");
			alert.getButtonTypes().setAll(buttonTypeOK);
			alert.showAndWait();
		}
		else {
			String chooseAssessment = assessmentChosen.getAssessTitle();
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation");
			alert.setHeaderText("You are to delete an assessment !!!");
			alert.setContentText("Are you sure to delete this assessment ???");
			ButtonType buttonTypeYes = new ButtonType("Yes");
			ButtonType buttonTypeNo = new ButtonType("No");
			alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
			Optional result = alert.showAndWait();
			
			if (result.get() == buttonTypeYes) {
				String sql = "DELETE FROM assessment WHERE courseID = ? AND title = ?;";
				try {
					PreparedStatement statement = connectDB.connectSqlite().prepareStatement(sql);
					statement.setString(1, courseIDforAssessment);
					statement.setString(2, chooseAssessment);
					statement.executeUpdate();
					statement.close();
				} catch (SQLException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				assessment.clear();
				question.clear();
				student.clear();
				rubricItem.clear();
				//update list
				try {			    	
					Statement stmt = connectDB.connectSqlite().createStatement();
					String sqlRefresh="select * from assessment where courseID = '" + courseIDforAssessment + "'";
					ResultSet rs=stmt.executeQuery(sqlRefresh);
					while(rs.next()){
						assessment.add(new Assessment(rs.getString(1), rs.getString(2), rs.getFloat(3), rs.getString(4), rs.getString(5)));
					}
				} catch(Exception e) {
					// TODO: handle exception
				}
				assessmentList.setItems(assessment);
			}
		}
	}
	// Event Listener on Button.onAction
	@FXML
	public void importRubric(ActionEvent event) {
		// TODO Autogenerated
		Course chooseCourse = courseList2.getSelectionModel().getSelectedItem();
		if(chooseCourse==null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("You have not chosen any course !!!");
			alert.setContentText("Please select a course first !!!");
			ButtonType buttonTypeOK = new ButtonType("OK");
			alert.getButtonTypes().setAll(buttonTypeOK);
			alert.showAndWait();
		}
		else {
			courseIDforRubric = chooseCourse.getCourseID();
			Start.getInstance().rubricImport();
		}
	}
	// Event Listener on Button.onAction
	@FXML
	public void newRubric(ActionEvent event) {
		// TODO Autogenerated
		Course chooseCourse = courseList2.getSelectionModel().getSelectedItem();
		if(chooseCourse == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("You have not chosen any course !!!");
			alert.setContentText("Please select a course first !!!");
			ButtonType buttonTypeOK = new ButtonType("OK");
			alert.getButtonTypes().setAll(buttonTypeOK);
			alert.showAndWait();
		}else {
			Start.getInstance().rubricNew();
		}
	}
	// Event Listener on Button.onAction
	@FXML
	public void deleteRubric(ActionEvent event) {
		// TODO Autogenerated
		Rubric rubricChosen = rubricList.getSelectionModel().getSelectedItem();
		if(rubricChosen == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("You have not chosen any rubric !!!");
			alert.setContentText("Please select a rubric first !!!");
			ButtonType buttonTypeOK = new ButtonType("OK");
			alert.getButtonTypes().setAll(buttonTypeOK);
			alert.showAndWait();
		}else {
			String chooseRubricID = rubricChosen.getRubricID();
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation");
			alert.setHeaderText("You are to delete a rubric !!!");
			alert.setContentText("Are you sure to delete this rubric ???");
			ButtonType buttonTypeYes = new ButtonType("Yes");
			ButtonType buttonTypeNo = new ButtonType("No");
			alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
			Optional result = alert.showAndWait();
			
			if(result.get() == buttonTypeYes) {
				String sql = "DELETE FROM rubric WHERE ID = ?; DELETE FROM item WHERE rubricID = ?;";
				try {
					PreparedStatement statement = connectDB.connectSqlite().prepareStatement(sql);
					statement.setString(1, chooseRubricID);
					statement.executeUpdate();
					
					sql = "DELETE FROM item WHERE rubricID = ?;";
					statement = connectDB.connectSqlite().prepareStatement(sql);
					statement.setString(2, chooseRubricID);
					statement.executeUpdate();
					statement.close();
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				rubrics.clear();
				
				try {
					Statement statement = connectDB.connectSqlite().createStatement();
					String sqlFresh = "Select * from rubric where courseID ='" + courseIDforRubric + "'";
					ResultSet rSet = statement.executeQuery(sqlFresh);
					while (rSet.next()) {
						rubrics.add(new Rubric(rSet.getString(1), rSet.getString(2)));
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				rubricItem2.clear();
				rubricList.setItems(rubrics);
			}
			
		}
	}
	// Event Listener on Button.onAction
	@FXML
	public void printAnalysis(ActionEvent event) {
		// TODO Autogenerated
		Start.getInstance().analysisPrint();
	}
	// Event Listener on Button.onAction
	@FXML
	public void exportAnalysis(ActionEvent event) {
		// TODO Autogenerated
		Course courseChooosen = courseList3.getSelectionModel().getSelectedItem();
		
		if (courseChooosen == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("You have not chosen a course !!!");
			alert.setContentText("Please choose one course to export the analysis !!!");
			ButtonType buttonTypeOK = new ButtonType("OK");
			alert.getButtonTypes().setAll(buttonTypeOK);
			alert.showAndWait();
		}
		
		else {
			courseIDChoosen = courseChooosen;
			
			Start.getInstance().analysisExport();
		}
	}
	// Event Listener on Button.onAction
	@FXML
	public void addCourse(ActionEvent event) {
		// TODO Autogenerated
		String course_title = CourseTitle.getText();
		if(course_title.equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("You are to add a course !!!");
			alert.setContentText("Please input course title !!!");
			ButtonType buttonTypeOK = new ButtonType("OK");
			alert.getButtonTypes().setAll(buttonTypeOK);
			alert.showAndWait();
		}
		else {
			boolean check = true;
			try {			    	
		    	Statement stmt = connectDB.connectSqlite().createStatement();
		    	String sql="select * from course where username = '" +  loginController.getUsername() + "'";
		    	ResultSet rs=stmt.executeQuery(sql);
		    	while(rs.next()){
		    		if(course_title.equals(rs.getString(2)))
		    			check = false;
				}
			} catch(SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			if(check==false) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText("You are to add a course !!!");
				alert.setContentText("This course already exists !!!");
				ButtonType buttonTypeOK = new ButtonType("OK");
				alert.getButtonTypes().setAll(buttonTypeOK);
				alert.showAndWait();
			}
			else {
				Connection connection = connectDB.connectSqlite();	
				String DB_url = "INSERT INTO course VALUES (NULL, ?, ?);";		
				String userName = loginController.getUsername();		
				try {
					PreparedStatement statement = connection.prepareStatement(DB_url);
					statement.setString(1, course_title);
					statement.setString(2, userName);			
					statement.executeUpdate();
				} catch (SQLException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				CourseTitle.clear();
				course.clear();
				try {			    	
					Statement stmt = connectDB.connectSqlite().createStatement();
					String sql="select * from course where username = '" +  loginController.getUsername() + "'";
					ResultSet rs=stmt.executeQuery(sql);
					while(rs.next()){
						course.add(new Course(rs.getString(1), rs.getString(2)));
					}
				} catch(Exception e) {
					// TODO: handle exception
				}
				courseList1.setItems(course);
				courseList2.setItems(course);
				courseList3.setItems(course);
				courseList4.setItems(course);		
				assessment.clear();
			}
		}
		
	}
	// Event Listener on Button.onAction
	@FXML
	public void deleteCourse(ActionEvent event) {
		// TODO Autogenerated
		Course chooseCourse = courseList4.getSelectionModel().getSelectedItem();
		if(chooseCourse==null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("You are to delete a course !!!");
			alert.setContentText("Please select a course first !!!");
			ButtonType buttonTypeOK = new ButtonType("OK");
			alert.getButtonTypes().setAll(buttonTypeOK);
			alert.showAndWait();
		}
		else {
			selectedCourseID = chooseCourse.getCourseID();
		
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation");
			alert.setHeaderText("You are to delete a course !!!");
			alert.setContentText("Are you sure to delete this course ???");
			ButtonType buttonTypeYes = new ButtonType("Yes");
			ButtonType buttonTypeNo = new ButtonType("No");
			alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);		
			Optional result = alert.showAndWait();
		
			if (result.get() == buttonTypeYes) {
				String sql = "DELETE FROM course WHERE title = ? AND username = ?;";
				try {
					PreparedStatement statement = connectDB.connectSqlite().prepareStatement(sql);
					statement.setString(1, chooseCourse.getCourseTitle());
					statement.setString(2, loginController.getUsername());
					statement.executeUpdate();
					statement.close();
				} catch (SQLException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				course.clear();
				//update list
				try {			    	
					Statement stmt = connectDB.connectSqlite().createStatement();
					String sqlRefresh="select * from course where username = '" +  loginController.getUsername() + "'";
					ResultSet rs=stmt.executeQuery(sqlRefresh);
					while(rs.next()){
						course.add(new Course(rs.getString(1), rs.getString(2)));
					}
				} catch(Exception e) {
					// TODO: handle exception
				}
				courseList1.setItems(course);
				courseList2.setItems(course);
				courseList3.setItems(course);
				courseList4.setItems(course);
				
				assessment.clear();
			} 
		}
		
	}
	// Event Listener on Button.onAction
	@FXML
	public void importRubricToCourse(ActionEvent event) {
		// TODO Autogenerated
		Course chooseCourse = courseList4.getSelectionModel().getSelectedItem();
		if(chooseCourse==null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("You are to import a rubric !!!");
			alert.setContentText("Please select a course first !!!");
			ButtonType buttonTypeOK = new ButtonType("OK");
			alert.getButtonTypes().setAll(buttonTypeOK);
			alert.showAndWait();
		}
		else {
			selectedCourseID = chooseCourse.getCourseID();
			Start.getInstance().courseImportRubric();
		}
	}
	// Event Listener on Button.onAction
	@FXML
	public void importStudentToCourse(ActionEvent event) {
		// TODO Autogenerated
		Course chooseCourse = courseList4.getSelectionModel().getSelectedItem();
		if(chooseCourse==null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("You are to import a student list !!!");
			alert.setContentText("Please select a course first !!!");
			ButtonType buttonTypeOK = new ButtonType("OK");
			alert.getButtonTypes().setAll(buttonTypeOK);
			alert.showAndWait();
		}
		else {
			selectedCourseID = chooseCourse.getCourseID();
			Start.getInstance().courseImportStudent();
		}
	}
	
	@FXML
	public void showStudentList() {
		Course courseChosen = courseList4.getSelectionModel().getSelectedItem();
		String currentCourseID = courseChosen.getCourseID();
		// TODO Auto-generated method stub
		student.clear();
		try {	    	
	    	Statement stmt = connectDB.connectSqlite().createStatement();
	    	String sql="select * from student where courseID = '" + currentCourseID + "'";
	    	ResultSet rs=stmt.executeQuery(sql);
	    	while(rs.next()){
	    		student.add(new Student(rs.getString(1), rs.getString(2)));
			}
		} catch(Exception e) {
			// TODO: handle exception
		}
		studentList4.setItems(student);
	}
	
	@FXML
	public void clickCourseList3() {
		Course courseChoosen = courseList3.getSelectionModel().getSelectedItem();
		String currentCourseID = courseChoosen.getCourseID();
		student.clear();
		assessmentResults.clear();
		try {	    	
	    	Statement stmt = connectDB.connectSqlite().createStatement();
	    	String sql="select * from student where courseID = '" + currentCourseID + "'";
	    	ResultSet rs=stmt.executeQuery(sql);
	    	while(rs.next()){
	    		student.add(new Student(rs.getString(1), rs.getString(2)));
			}
		} catch(Exception e) {
			// TODO: handle exception
		}
		
		//update the letterGrade
		SortGrade.SortFinalGrade(Integer.parseInt(courseChoosen.getCourseID()));
		
		studentList3.setItems(student);
	}
	
	@FXML
	public void clickStudentList3() {
		Course courseChoosen = courseList3.getSelectionModel().getSelectedItem();
		Student studentChoosen = studentList3.getSelectionModel().getSelectedItem();
		
		Connection connection = connectDB.connectSqlite();
		
		List<AssessmentResult> assessmentResultsArrayList = new ArrayList<>();
		
		
		//read the assessmentID and grade
		String sql = "SELECT * FROM assessment INNER JOIN assessment_student ON assessment.ID = assessment_student.assessmentID WHERE studentID = ? AND courseID = ?;";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, Integer.parseInt(studentChoosen.getStudentID()));
			preparedStatement.setInt(2, Integer.parseInt(courseChoosen.getCourseID()));
			
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				AssessmentResult temp = new AssessmentResult(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(9));
				assessmentResultsArrayList.add(temp);
			}
			
			resultSet.close();
			preparedStatement.close();
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		//load the data inside the table view
		assessmentResults.clear();
		
		assessmentTitle.setCellValueFactory(new PropertyValueFactory<AssessmentResult, String>("Title"));
		assessmentWeight.setCellValueFactory(new PropertyValueFactory<AssessmentResult, String>("Weight"));
		assessmentGrade.setCellValueFactory(new PropertyValueFactory<AssessmentResult, String>("Grade"));
		for (AssessmentResult resultItem: assessmentResultsArrayList) {
			assessmentResults.add(resultItem);
		}
		assessmentResultList.setItems(assessmentResults);
		
		//Show the total grade
		float finalGrade = 0;
		String letterGrade = null;
		try {
			sql = "SELECT * FROM grade WHERE studentID = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setInt(1, Integer.parseInt(studentChoosen.getStudentID()));
			
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				finalGrade = rs.getFloat(1);
				letterGrade = rs.getString(2);
			}
			
			ResultDisplay.setText("The final grade is: " + finalGrade + ", The letter grade is: " + letterGrade);
			connection.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	@FXML
	public void SearchResult(ActionEvent event) {
		
		String searchInput = SearchInput.getText();
		String chooseSearch = chooseSearchList.getValue();
		
		
		if (searchInput == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Search box empty !!!");
			alert.setContentText("Please input a item to search !!!");
			ButtonType buttonTypeOK = new ButtonType("OK");
			alert.getButtonTypes().setAll(buttonTypeOK);
			alert.showAndWait();
		}
		else {
			searchResult.clear();
			Connection connection = connectDB.connectSqlite();
			String sql = null;
			
			if (chooseSearch.equals("CourseName")) {
				sql = "SELECT * FROM (grade INNER JOIN course ON  grade.courseID = course.ID) INNER JOIN student ON grade.studentID = student.ID WHERE course.title = ?";
				try {
					PreparedStatement preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setString(1, searchInput);
					
					ResultSet resultSet = preparedStatement.executeQuery();
					while (resultSet.next()) {
						
						SearchResultMain tempResult = new SearchResultMain(resultSet.getString(3), resultSet.getString(9), resultSet.getString(6), resultSet.getString(1), resultSet.getString(2));
						searchResult.add(tempResult);
					}
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			else if (chooseSearch.equals("StudentID")) {
				sql = "SELECT * FROM (grade INNER JOIN course ON  grade.courseID = course.ID) INNER JOIN student ON grade.studentID = student.ID WHERE student.ID = ?";
				try {
					PreparedStatement preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setInt(1, Integer.parseInt(searchInput));
					
					ResultSet resultSet = preparedStatement.executeQuery();
					while (resultSet.next()) {
						SearchResultMain tempResult = new SearchResultMain(resultSet.getString(3), resultSet.getString(9), resultSet.getString(6), resultSet.getString(1), resultSet.getString(2));
						
						searchResult.add(tempResult);
					}
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			else if (chooseSearch.equals("StudentName")) {
				sql = "SELECT * FROM (grade INNER JOIN course ON  grade.courseID = course.ID) INNER JOIN student ON grade.studentID = student.ID WHERE student.name = ?";
				try {
					PreparedStatement preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setString(1, searchInput);
					
					ResultSet resultSet = preparedStatement.executeQuery();
					while (resultSet.next()) {
						SearchResultMain tempResult = new SearchResultMain(resultSet.getString(3), resultSet.getString(9), resultSet.getString(6), resultSet.getString(1), resultSet.getString(2));
						
						
						searchResult.add(tempResult);
					}
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			else {
				System.out.println("Error, can not run");
			}
			
			
			try {
				
				connection.close();
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			searchResultList.setItems(searchResult);
			
		}
	}
	
	
	
	@FXML
	public void refreshData(ActionEvent event) {
		Start.getInstance().jump();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		// TODO Auto-generated method stub
		
				try {			    	
			    	Statement stmt = connectDB.connectSqlite().createStatement();
			    	
			    	//initialize CourseList
			    	String sql="select * from course where username = '" +  loginController.getUsername() + "'";
			    	ResultSet rs=stmt.executeQuery(sql);
			    	while(rs.next()){
			    		course.add(new Course(rs.getString(1), rs.getString(2)));
					}
			    	
			    	stmt.close();
			    	rs.close();
			    	
				} catch(SQLException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				courseList1.setItems(course);
				courseList2.setItems(course);
				courseList3.setItems(course);
				courseList4.setItems(course);
				
				//Search page
				chooseSearchList.getItems().addAll("CourseName", "StudentID", "StudentName");
				chooseSearchList.setValue("CourseName");
				
	}
	
}
