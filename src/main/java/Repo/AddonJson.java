package Repo;

/**
 *
 */
public class AddonJson
{
    private String name;
    private String version;
    private String creators;
    private String gh_username;
    private String gh_reponame;
    private String download_url;
    private String icon_url;
    private String description;
    private String file_launch;
    private String programming_language;

    private AddonJson()
    {

    }

    public AddonJson(String name, String version, String creators, String gh_username, String gh_reponame, String download_url, String icon_url, String description, String file_launch, String programming_language)
    {
        this.name = name;
        this.version = version;
        this.creators = creators;
        this.gh_username = gh_username;
        this.gh_reponame = gh_reponame;
        this.download_url = download_url;
        this.icon_url = icon_url;
        this.description = description;
        this.file_launch = file_launch;
        this.programming_language = programming_language;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getCreators()
    {
        return creators;
    }

    public void setCreators(String creators)
    {
        this.creators = creators;
    }

    public String getGh_username()
    {
        return gh_username;
    }

    public void setGh_username(String gh_username)
    {
        this.gh_username = gh_username;
    }

    public String getGh_reponame()
    {
        return gh_reponame;
    }

    public void setGh_reponame(String gh_reponame)
    {
        this.gh_reponame = gh_reponame;
    }

    public String getDownload_url()
    {
        return download_url;
    }

    public void setDownload_url(String download_url)
    {
        this.download_url = download_url;
    }

    public String getIcon_url()
    {
        return icon_url;
    }

    public void setIcon_url(String icon_url)
    {
        this.icon_url = icon_url;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getFile_launch()
    {
        return file_launch;
    }

    public void setFile_launch(String file_launch)
    {
        this.file_launch = file_launch;
    }

    public String getProgramming_language()
    {
        return programming_language;
    }

    public void setProgramming_language(String programming_language)
    {
        this.programming_language = programming_language;
    }
}
