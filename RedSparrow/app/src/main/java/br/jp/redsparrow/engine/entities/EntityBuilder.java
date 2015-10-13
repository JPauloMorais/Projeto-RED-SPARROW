package br.jp.redsparrow.engine.entities;

import java.util.HashMap;
import java.util.Map;

import br.jp.redsparrow.engine.World;

/**
 * Created by JoaoPaulo on 08/10/2015.
 */
public class EntityBuilder
{
	//TODO:  Maneira de criar algum tipo de hierarquia
	private static final Map<String, BuildInfo> entityBuildInfo = new HashMap<String, BuildInfo>();

	public static void createType(String name, EntityType entityType)
	{
		BuildInfo buildInfo = new BuildInfo();
		buildInfo.worldId = World.addEntityType(entityType);
		buildInfo.type = entityType;
		entityBuildInfo.put(name, buildInfo);
	}

	public static Entity build(String typeName)
	{
		entityBuildInfo.get(typeName);
		return null;
	}

	static class BuildInfo
	{
		public int worldId = - 1;
		public EntityType type;
	}
}
