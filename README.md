## What is Hancock

Hancock is a Discord Bot that ships no features but an api to build on.

### Build and Setup

Just clone the repository and run maven. After maven finished, run Hancock the first time, you will notice that an error gets thrown and Hancock stopped. A folder relative to Hancock was created, inside is ``hancock.cfg``.

Open it with your favourite editor and edit the bot api token, you receive <a href="https://discordapp.com/developers/applications/">here</a>.

You will notice another folder that is called ``features`` here are all feature extending applications for hancock are stored. The folder can also be configured in the ``features.cfg``.

### Feature

A Feature is a Plugin that gets loaded from the configurated folder to extend Hancock's functionally.

Example feature class

```Java
@HancockFeature(name = "Name", description = "Description", author = "Author", version = 1.0)
public final class ExampleFeature extends Feature {
    @Override 
    public void onEnable() {
       System.out.println(this.getName() + " v" + this.getVersion() + " has been enabled."); 
    }
    
    @Override
    public void onDisable() {
        System.out.println(this.getName() + " v" + this.getVersion() + " has been disabled.");
    }
}
```

A fully JavaDoc can be found <a href="http://hybridhacker.net/hancock/apidocs/">here</a>.
