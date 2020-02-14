package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Start extends Application
{
	private Stage stage;

	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		stage = primaryStage;
        login();
        stage.show();
        
	}
	
	private static Start instance;

    public Start() 
    {
        instance = this;
    }

    public static Start getInstance() 
    {
        return instance;
    }
	
	public void login()
	{
		try 
		{
			replaceSceneContent("login.fxml");
		} 
		catch (Exception e) 
		{
			System.out.println(e);
		}
	}
	
	public void jump()
	{
		try 
		{
			replaceSceneContent("main.fxml");
		} 
		catch (Exception e) 
		{
			System.out.println(e);
		}
	}
	
	public void analysisExport()
	{
		try 
		{
			replaceSceneContent("analysisExport.fxml");
		} 
		catch (Exception e) 
		{
			System.out.println(e);
		}
	}
	
	public void analysisPrint()
	{
		try 
		{
			replaceSceneContent("analysisPrint.fxml");
		} 
		catch (Exception e) 
		{
			System.out.println(e);
		}
	}
	
	public void assessmentEdit()
	{
		try 
		{
			replaceSceneContent("assessmentEdit.fxml");
		} 
		catch (Exception e) 
		{
			System.out.println(e);
		}
	}
	
	public void assessmentNew()
	{
		try 
		{
			replaceSceneContent("assessmentNew.fxml");
		} 
		catch (Exception e) 
		{
			System.out.println(e);
		}
	}
	
	public void assessmentNext()
	{
		try 
		{
			replaceSceneContent("assessmentNext.fxml");
		} 
		catch (Exception e) 
		{
			System.out.println(e);
		}
	}
	
	public void courseImportRubric()
	{
		try 
		{
			replaceSceneContent("courseImportRubric.fxml");
		} 
		catch (Exception e) 
		{
			System.out.println(e);
		}
	}
	
	public void courseImportStudent()
	{
		try 
		{
			replaceSceneContent("courseImportStudent.fxml");
		} 
		catch (Exception e) 
		{
			System.out.println(e);
		}
	}
	
	public void rubricImport()
	{
		try 
		{
			replaceSceneContent("rubricImport.fxml");
		} 
		catch (Exception e) 
		{
			System.out.println(e);
		}
	}
	
	public void rubricNew()
	{
		try 
		{
			replaceSceneContent("rubricNew.fxml");
		} 
		catch (Exception e) 
		{
			System.out.println(e);
		}
	}
	
	public static void main(String[] args) {
        launch(args);
    }
	
	private Parent replaceSceneContent(String fxml) throws Exception 
	{
        Parent page = (Parent) FXMLLoader.load(Start.class.getResource(fxml), null, new JavaFXBuilderFactory());
        Scene scene = stage.getScene();
        if (scene == null) 
        {
            scene = new Scene(page);
            stage.setScene(scene);
        } 
        else 
        {
            stage.getScene().setRoot(page);
        }
        stage.sizeToScene();
        stage.setResizable(false);
        return page;
	}
}
