import Repo.AddonJson;
import Repo.FilterJson;
import Repo.JsonConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.omg.CORBA.Object;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Controller implements Initializable
{

    @FXML
    private TextField DISPLAY_NAME;

    @FXML
    private TextField VERSION;

    @FXML
    private TextField CREATORS;

    @FXML
    private TextField PROGRAMMING_LANGUAGE;

    @FXML
    private TextField POE_VERSION;

    @FXML
    private Button bFilter;

    @FXML
    private TextField LAUNCH_LOCATION;

    @FXML
    private TextField GH_USERNAME;

    @FXML
    private TextField GH_REPO_NAME;

    @FXML
    private TextField DOWNLOAD_URL;

    @FXML
    private Button bAddon;

    @FXML
    private Button bSaveRepo;

    @FXML
    private Text txtCount;

    @FXML
    private Button bImportRepo;

    @FXML
    private TextField ICON_URL;

    @FXML
    private TextArea DESCRIPTION;

    @FXML
    private Text charLimitText;

    @FXML
    private Button bLOAD;

    private ArrayList<FilterJson> filters = new ArrayList();
    private ArrayList<AddonJson> addons = new ArrayList<>();

    public void countRepoSize()
    {
        Platform.runLater(() ->
        {
            String text = "Addons: %a | Filters: %f";
            text = text.replace("%a", addons.size() + "");
            text = text.replace("%f", filters.size() + "");
            txtCount.setText(text);
        });
    }

    public void AddFilter()
    {
        FilterJson filter = new FilterJson(DISPLAY_NAME.getText(),
            VERSION.getText(),
            CREATORS.getText(),
            GH_USERNAME.getText(),
            GH_REPO_NAME.getText(),
            DOWNLOAD_URL.getText(),
            POE_VERSION.getText(),
            DESCRIPTION.getText(),
            ICON_URL.getText());

        // Check if it's already here, if it is update it!
        for (FilterJson f : filters)
        {
            if (f.getName().equals(DISPLAY_NAME.getText()))
            {
                filters.remove(f);
                filters.add(filter);
                clearAndCount();
                return;
            }
        }

        filters.add(filter);
        clearAndCount();
    }

    private void clearForm()
    {
        Platform.runLater(() ->
        {
            DISPLAY_NAME.setText("");
            VERSION.setText("");
            CREATORS.setText("");
            GH_USERNAME.setText("");
            GH_REPO_NAME.setText("");
            DOWNLOAD_URL.setText("");
            POE_VERSION.setText("");
            clearDescription();
            ICON_URL.setText("");
            LAUNCH_LOCATION.setText("Addons/%d/%v/<your.exe/jar/ahk/etc>");
            PROGRAMMING_LANGUAGE.setText("");
        });
    }

    private void clearDescription()
    {
        DESCRIPTION.setText("");
        charLimitText.setText("0/240");
    }

    public void AddAddon()
    {
        AddonJson addon = new AddonJson(DISPLAY_NAME.getText(),
                                        VERSION.getText(),
                                        CREATORS.getText(),
                                        GH_USERNAME.getText(),
                                        GH_REPO_NAME.getText(),
                                        DOWNLOAD_URL.getText(),
                                        ICON_URL.getText(),
                                        DESCRIPTION.getText(),
                                        LAUNCH_LOCATION.getText(),
                                        PROGRAMMING_LANGUAGE.getText());

        for (AddonJson a : addons)
        {
            if (a.getName().equals(addon.getName()))
            {
                addons.remove(a);
                addons.add(addon);
                clearAndCount();
                return;
            }
        }
        addons.add(addon);
        clearAndCount();
    }

    public void clearAndCount()
    {
        clearForm();
        countRepoSize();
    }

    public void SaveRepo()
    {
        File f = browse("Select a folder to save the repos to...");
        File addons_json = new File(f.toString() + "/addons.json");
        File filters_json = new File(f.toString() + "/filters.json");
        ObjectMapper objectMapper = new ObjectMapper();

        AddonJson[] addons_array = new AddonJson[addons.size()];
        for (int c = 0; c < addons.size(); c++)
        {
            addons_array[c] = addons.get(c);
        }

        FilterJson[] filters_array = new FilterJson[filters.size()];
        for (int c = 0; c < filters.size(); c++)
        {
            filters_array[c] = filters.get(c);
        }


        try
        {
            if (addons_array.length > 0)
                objectMapper.writeValue(addons_json, addons_array);
            if (filters_array.length > 0)
                objectMapper.writeValue(filters_json, filters_array);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            Runtime.getRuntime().exec("explorer " + f.toString());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public void ImportRepo()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open filters.json or addons.json");
        File f = fileChooser.showOpenDialog(Launcher.stage);

        if (f.getName().equals("filters.json"))
        {
            try
            {
                FilterJson[] array = JsonConverter.converToFilters(f);
                filters.addAll(Arrays.asList(array));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else if (f.getName().equals("addons.json"))
        {
            try
            {
                AddonJson[] array = JsonConverter.convertToAddons(f);
                addons.addAll(Arrays.asList(array));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            return;
        }

        countRepoSize();
    }

    /**
     * Method for Opening the DirectoryChooser.
     */
    public File browse(String title)
    {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(title);
        return directoryChooser.showDialog(Launcher.stage);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        clearAndCount();
        charLimitDaemon();
    }

    private void charLimitDaemon()
    {
        Runnable r = () ->
        {
            String cached_text = "";
            while (true)
            {
                try
                {
                    Thread.sleep(300L);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                if (!cached_text.equals(DESCRIPTION.getText()))
                {
                    cached_text = DESCRIPTION.getText();
                    Platform.runLater(() -> updateWordCount());
                }
            }
        };
        Thread t = new Thread(r);
        t.setDaemon(true);
        t.start();
    }

    public void updateWordCount()
    {
        charLimitText.setText(DESCRIPTION.getText().length() + "/" + 240);

        if (DESCRIPTION.getText().length() > 240)
        {
            DESCRIPTION.setText(DESCRIPTION.getText().substring(0, 240));
            DESCRIPTION.end();
        }

    }

    public void loadByName()
    {
        String text = DISPLAY_NAME.getText();

        for (AddonJson a : addons)
        {
            if (a.getName().equals(text))
            {
                setAddonInfo(a);
            }
        }

        for (FilterJson f : filters)
        {
            if (f.getName().equals(text))
            {
                setAddonInfo(f);
            }
        }
    }

    public void setAddonInfo(AddonJson a)
    {
        Platform.runLater(() ->
        {
            setData(a.getName(), a.getVersion(), a.getCreators(), a.getGh_username(), a.getGh_reponame(), a.getDownload_url(), a.getDescription(), a.getIcon_url());
            LAUNCH_LOCATION.setText(a.getFile_launch());
            PROGRAMMING_LANGUAGE.setText(a.getProgramming_language());
        });
    }

    public void setAddonInfo(FilterJson f)
    {
        Platform.runLater(() ->
        {
            setData(f.getName(), f.getVersion(), f.getCreators(), f.getGh_username(), f.getGh_repository(), f.getDownload_link(), f.getDescription(), f.getIcon_url());
            POE_VERSION.setText(f.getPoe_version());
        });
    }

    public void setData(String display_name, String version, String creators, String gh_username, String gh_reponame, String download_url, String description, String icon_url)
    {
        DISPLAY_NAME.setText(display_name);
        VERSION.setText(version);
        CREATORS.setText(creators);
        GH_USERNAME.setText(gh_username);
        GH_REPO_NAME.setText(gh_reponame);
        DOWNLOAD_URL.setText(download_url);
        DESCRIPTION.setText(description);
        ICON_URL.setText(icon_url);
    }
}
