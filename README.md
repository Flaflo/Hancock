## What is Hancock

Hancock is a Discord Bot with a plugin api to build on.

### Feature

A Feature is a Plugin that gets loaded from the configurated folder to extend Hancock's functionally.

Example feature class
```
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
