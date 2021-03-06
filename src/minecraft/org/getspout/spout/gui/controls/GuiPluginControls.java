package org.getspout.spout.gui.controls;

import org.getspout.spout.client.SpoutClient;
import org.spoutcraft.spoutcraftapi.Spoutcraft;
import org.spoutcraft.spoutcraftapi.addon.Addon;
import org.spoutcraft.spoutcraftapi.gui.Button;
import org.spoutcraft.spoutcraftapi.gui.CheckBox;
import org.spoutcraft.spoutcraftapi.gui.GenericButton;
import org.spoutcraft.spoutcraftapi.gui.GenericCheckBox;
import org.spoutcraft.spoutcraftapi.gui.GenericLabel;
import org.spoutcraft.spoutcraftapi.gui.Label;
import org.spoutcraft.spoutcraftapi.gui.WidgetAnchor;

import net.minecraft.src.GuiControls;
import net.minecraft.src.GuiScreen;

public class GuiPluginControls extends GuiScreen {
	GuiScreen parentScreen;
	Button closeButton;
	Label description;
	Label title;
	PluginControlsList list;
	CheckBox box;
	
	public GuiPluginControls(GuiControls parent) {
		super();
		parentScreen = parent;
	}
	
	public void initGui(){
		boolean wasSandboxed = SpoutClient.isSandboxed();
		if(wasSandboxed) {
			SpoutClient.disableSandbox();
		}
		Addon spoutcraft = Spoutcraft.getAddonManager().getAddon("Spoutcraft");
		title = new GenericLabel("Plugin Controls");
		int txtwidth = Spoutcraft.getMinecraftFont().getTextWidth("Plugin Controls");
		title.setX(width / 2 - txtwidth / 2 ).setY(10).setHeight(20).setWidth(200);
		getScreen().attachWidget(spoutcraft, title);
		
		closeButton = new GenericButton("Done");
		closeButton.setAlign(WidgetAnchor.CENTER_CENTER);
		closeButton.setWidth(50).setHeight(20).setX(10).setY(height-30);
		getScreen().attachWidget(spoutcraft, closeButton);
		description = new GenericLabel("Doubleclick a keybinding and press a key to assign!");
		description.setHeight(20).setWidth(200).setX(70).setY(height-30);
		getScreen().attachWidget(spoutcraft, description);
		
		list = new PluginControlsList(this);
		list.setX(0).setY(32).setWidth(width).setHeight(height - 32 - 40);
		getScreen().attachWidget(spoutcraft, list);
		
		box = new GenericCheckBox("Only current server");
		box.setWidth(150).setHeight(20);
		box.setX(width - 160).setY(list.getY() - 20);
		getScreen().attachWidget(spoutcraft, box);
		
		if (list.manager.getAllBindings().size() == 0) {
			description.setText("You don't have any plugin keybindings. Plugin Keybindings are\nregistered by plugins and you can edit the key which summons them.\nIf you want Shortcuts, go back and select \"Shortcuts\".");
		}
		if(wasSandboxed) {
			SpoutClient.enableSandbox();
		}
	}
	
	@Override
	public void drawScreen(int x, int y, float z) {
		drawDefaultBackground();
	}

	@Override
	protected void buttonClicked(Button btn) {
		if(btn.equals(closeButton)){
			mc.displayGuiScreen(parentScreen);
		}
		if(btn.equals(box)) {
			list.setFilered(box.isChecked());
			list.updateBindings();
		}
	}
}
