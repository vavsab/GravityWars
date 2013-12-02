package vavsab.gravitywars.game.maps;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Currency;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.R.menu;
import android.content.Context;
import android.content.res.AssetManager;

import com.badlogic.gdx.utils.Array;

public class LevelManager {
	private static Context context;
	private static Array<String> icons;
	private static Array<String> names;
	
 	public static void setContext(Context _context) {
 		context = _context;
 	}

 	public static Array<String> getMapsIconsList() {
 		return icons;
 	}
 	
 	public static Array<String> getMapsNamesList() {
 		return names;
 	}
 	
	public static Array<String> getMapsPathList() {
		Array<String> maps = new Array<String>();
		icons = new Array<String>();
		names = new Array<String>();
		XmlPullParser xpp;
		try {
			AssetManager am = context.getAssets();
			InputStream is = am.open("maps/maps.xml");
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			xpp = factory.newPullParser();
    		xpp.setInput(is, null);

			while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
				switch (xpp.getEventType()) {
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					String name = xpp.getName();
					if (name.equals("mapset"))
						for (int i = 0; i < xpp.getAttributeCount(); i++) {
						    String attr = xpp.getAttributeName(i);
							if (attr.equals("path"))
								maps.add(xpp.getAttributeValue(i));
							else
							if (attr.equals("icon"))
								icons.add(xpp.getAttributeValue(i));
							else
							if (attr.equals("name"))
								names.add(xpp.getAttributeValue(i));
						}
					break;
				case XmlPullParser.END_TAG:
					break;
				case XmlPullParser.TEXT:
					break;
				default:
					break;
				}
				xpp.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return maps;
	}
	
	public static RoundConfig getRoundConfig(String roundPath) {
		RoundConfig cfg = new RoundConfig();
		MapConfig curMap = new MapConfig();
		SpawnPoint curSpawn = new SpawnPoint();
		PlanetConfig curPlanet = new PlanetConfig();
		XmlPullParser xpp;
		try {
			AssetManager am = context.getAssets();
			InputStream is = am.open("maps/"+ roundPath + "/main.xml");
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			xpp = factory.newPullParser();
    		xpp.setInput(is, null);

			while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
				switch (xpp.getEventType()) {
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					String name = xpp.getName();
					Class parsingClass = MapConfig.class;
					Object instance = new Object();
					
					if (name.equals("mapset")) {
						parsingClass = RoundConfig.class;
				    	instance = cfg;
					}
					else
					if (name.equals("map")) {
						parsingClass = MapConfig.class;
						curMap = new MapConfig();
				    	instance = curMap;
					}
					else
					if (name.equals("spawn-point")) {
						parsingClass = SpawnPoint.class;
						curSpawn = new SpawnPoint();
				    	instance = curSpawn;
					}
					else
					if (name.equals("planet")) {
						parsingClass = PlanetConfig.class;
						curPlanet = new PlanetConfig();
				    	instance = curPlanet;
					}
					
						for (int i = 0; i < xpp.getAttributeCount(); i++) {
						    String attr = xpp.getAttributeName(i);
						    String value = xpp.getAttributeValue(i);
						    Field field = parsingClass.getField(attr);
						    
						    	
						    String parsingClassName = field.getType().getName();
						    if (parsingClassName.equals("java.lang.String"))
						    {
						    	field.set(instance, value);
						    }
						    else
						    if (parsingClassName.equals("java.lang.Float"))
						    {
						    	field.set(instance, Float.parseFloat(value));
						    }
						    else
							if (parsingClassName.equals("java.lang.Integer"))
						    {
						    	field.set(instance, Integer.parseInt(value));
						    }
						}
					break;
				case XmlPullParser.END_TAG:
					if (xpp.getName().equals("map")) {
						cfg.maps.add(curMap);
					}
					else
					if (xpp.getName().equals("spawn-point")) {
						curMap.spawns.add(curSpawn);
					}
					else
					if (xpp.getName().equals("planet")) {
						curMap.planets.add(curPlanet);
					}
					break;
				case XmlPullParser.TEXT:
					break;
				default:
					break;
				}
				xpp.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cfg;
	}

}
