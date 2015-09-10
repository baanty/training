/*******************************************************************************
 * Copyright (c) 2013 QPark Consulting  S.a r.l.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0.
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Bernhard Hausen - Initial API and implementation
 *
 ******************************************************************************/
package com.qpark.maven.plugin.xmapper;

import java.io.File;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.apache.maven.plugin.logging.Log;

import com.qpark.maven.Util;
import com.qpark.maven.xmlbeans.ComplexType;
import com.qpark.maven.xmlbeans.ComplexTypeChild;
import com.qpark.maven.xmlbeans.XsdsUtil;

/**
 * @author bhausen
 */
public abstract class AbstractMappingTypeGenerator extends AbstractGenerator {
	protected final ComplexType complexType;
	protected final String interfaceName;
	protected final String implName;

	public AbstractMappingTypeGenerator(final XsdsUtil config,
			final ComplexType complexType,
			final ComplexContentList complexContentList, final Log log) {
		super(config, complexContentList, log);
		this.complexType = complexType;
		this.packageName = this.getPackageNameInterface();
		this.packageNameImpl = this.getPackageNameImpl();
		this.interfaceName = getInterfaceName(complexType);
		this.implName = this.getImplName();
	}

	@Override
	public String getInterfaceName() {
		return this.interfaceName;
	}

	protected String getReturnValueClassName() {
		String returnValueClassName = null;
		for (ComplexTypeChild child : this.complexType.getChildren()) {
			if (child.getChildName().equals("return")) {
				returnValueClassName = child.getComplexType().getClassName();
				break;
			}
		}
		return returnValueClassName;
	}

	protected boolean isReturnValueList() {
		boolean returnValueIsList = false;
		for (ComplexTypeChild child : this.complexType.getChildren()) {
			if (child.getChildName().equals("return")) {
				returnValueIsList = child.isList();
				break;
			}
		}
		return returnValueIsList;
	}

	/**
	 * @param children
	 * @return
	 */
	protected String getDefaultDefinitions(final String modifier) {
		StringBuffer sb = new StringBuffer(1024);
		for (ComplexTypeChild child : this
				.getDefaultingChildren(this.complexType)) {
			sb.append("\t/** The default value of ");
			sb.append(child.getJavaChildName());
			sb.append(". */\n");
			sb.append("\t");
			if (modifier != null && modifier.trim().length() > 0) {
				sb.append(modifier);
				sb.append(" ");
			}
			sb.append("String DEFAULT_");
			sb.append(child.getJavaChildName().toUpperCase());
			sb.append(" =\"");
			sb.append(child.getDefaultValue().getStringValue());
			sb.append("\";\n");
		}
		if (sb.length() > 0) {
			sb.append("\n");
		}
		return sb.toString();
	}

	protected abstract String getMappingType();

	protected String getSeeInterfaceJavaDoc(
			final List<ComplexTypeChild> children) {
		StringBuffer sb = new StringBuffer(256);
		sb.append("\t * @see ");
		sb.append(this.getFqInterfaceName());
		sb.append("#");
		sb.append(this.getMethodName());
		sb.append("(");
		int i = 0;
		for (ComplexTypeChild child : children) {
			if (child.getDefaultValue() == null) {
				if (i > 0) {
					sb.append(", ");
				}
				sb.append(child.getComplexType().getClassNameFullQualified());
				i++;
			}
		}
		sb.append(")\n");
		return sb.toString();
	}

	private static String getInterfaceName(final ComplexType ct) {
		StringBuffer sb = new StringBuffer(128);
		if (ct.getClassName().contains("MappingType")) {
			sb.append(Util.capitalizePackageName(ct.getClassName().substring(0,
					ct.getClassName().length() - "MappingType".length())));
		} else {
			sb.append(Util.capitalizePackageName(ct.getClassName()));
		}
		sb.append("Mapper");

		return sb.toString();
	}

	protected abstract String getMethodName();

	protected String getSetterStatements(final String varName,
			final List<ComplexTypeChild> children) {
		StringBuffer sb = new StringBuffer(256);
		for (ComplexTypeChild child : children) {
			if (child.isList()) {
				sb.append("\t\t");
				sb.append(varName);
				sb.append(".");
				sb.append(child.getGetterName());
				sb.append("().addAll(");
				sb.append(child.getJavaChildName());
				sb.append(");\n");
			} else if (child.getDefaultValue() != null) {
				String objAndSetter = new StringBuffer(36).append(varName)
						.append(".").append(child.getSetterName()).toString();
				sb.append("\t\t");
				sb.append(XsdsUtil.getXmlObjectAsSetterParam(objAndSetter,
						child.getDefaultValue()));
				sb.append("\n");
			} else {
				sb.append("\t\t");
				sb.append(varName);
				sb.append(".");
				sb.append(child.getSetterName());
				sb.append("(");
				sb.append(child.getJavaChildName());
				sb.append(");\n");
			}
			// sb.append("//").append(child.getJavaChildName()).append(" ")
			// .append(child.getSetterName()).append(" ")
			// .append(child.getGetterName()).append(" ")
			// .append(child.isList()).append("\n");
		}
		return sb.toString();
	}

	protected String getChildrenImports(final List<ComplexTypeChild> children) {
		StringBuffer sb = new StringBuffer(256);
		TreeSet<String> imports = new TreeSet<String>();
		for (ComplexTypeChild child : children) {
			if (!child.getComplexType().getClassNameFullQualified()
					.startsWith("java.lang.")
					&& !"interfaceName".equals(child.getChildName())
					&& child.getComplexType().getPackageName() != null
					&& child.getComplexType().getPackageName().length() > 0) {
				sb.setLength(0);
				sb.append("import ");
				sb.append(child.getComplexType().getClassNameFullQualified());
				sb.append(";\n");
				imports.add(sb.toString());
			}
		}
		sb.setLength(0);
		for (String string : imports) {
			sb.append(string);
		}
		return sb.toString();
	}

	protected Set<String> getImplImports(
			final List<Entry<ComplexTypeChild, List<ComplexTypeChild>>> children) {
		Set<String> imports = this.getImplImports(children, this
				.getFqInterfaceName(), this.complexType
				.getClassNameFullQualified(),
				new StringBuffer(64).append(this.complexType.getPackageName())
						.append(".ObjectFactory").toString()
				// ,"org.springframework.beans.factory.annotation.Autowired"
				, "org.springframework.stereotype.Component"
		// ,"org.apache.commons.beanutils.PropertyUtils"
				);
		return imports;
	}

	@Override
	protected List<Entry<ComplexTypeChild, List<ComplexTypeChild>>> getChildrenTree() {
		List<Entry<ComplexTypeChild, List<ComplexTypeChild>>> list = new ArrayList<Entry<ComplexTypeChild, List<ComplexTypeChild>>>();
		Entry<ComplexTypeChild, List<ComplexTypeChild>> grandchild;
		for (ComplexTypeChild child : GeneratorMapper
				.getValidChildren(this.complexType)) {
			grandchild = new SimpleEntry<ComplexTypeChild, List<ComplexTypeChild>>(
					child, GeneratorMapper.getValidChildren(child
							.getComplexType()));
			list.add(grandchild);
		}
		return list;
	}

	protected List<ComplexTypeChild> getChildren() {
		return GeneratorMapper.getValidChildren(this.complexType);
	}

	protected Set<String> getInterfaceImports(
			final List<ComplexTypeChild> children) {
		Set<String> imports = this.getInterfaceImports(children,
				this.complexType.getClassNameFullQualified());
		return imports;
	}

	protected String getChildrenImporxts(final List<ComplexTypeChild> children) {
		StringBuffer sb = new StringBuffer(256);
		TreeSet<String> imports = new TreeSet<String>();
		for (ComplexTypeChild child : children) {
			if (!child.getComplexType().getClassNameFullQualified()
					.startsWith("java.lang.")
					&& !"interfaceName".equals(child.getChildName())
					&& child.getComplexType().getPackageName() != null
					&& child.getComplexType().getPackageName().length() > 0) {
				sb.setLength(0);
				sb.append("import ");
				sb.append(child.getComplexType().getClassNameFullQualified());
				sb.append(";\n");
				imports.add(sb.toString());
			}
		}
		sb.setLength(0);
		for (String string : imports) {
			sb.append(string);
		}
		return sb.toString();
	}

	public Entry<String, String> generateInterface(
			final File outputInterfacesDirectory) {
		this.log.debug("+generateInterface");

		List<ComplexTypeChild> children = this.getChildren();
		StringBuffer sb = new StringBuffer(1024);
		sb.append("package ");
		sb.append(this.packageName);
		sb.append(";\n");
		sb.append("\n");

		Set<String> importedClasses = this.getInterfaceImports(children);
		importedClasses
				.add("com.springsource.insight.annotation.InsightOperation");

		for (String importedClass : importedClasses) {
			sb.append("import ").append(importedClass).append(";\n");
		}

		sb.append("\n");
		sb.append("/**\n");
		sb.append(" * The ");
		sb.append(Util.splitOnCapital(this.interfaceName));
		sb.append(".\n");
		if (this.complexType.getAnnotationDocumentation() != null) {
			sb.append(" * <p/>\n");
			sb.append(" * <p/>\n");
			sb.append(toJavadocHeader(this.complexType
					.getAnnotationDocumentation()));
		}
		sb.append(" * <p/>\n");
		sb.append(" * The returned {@link ");
		sb.append(this.complexType.getClassName());
		sb.append("} is defined as \n");
		sb.append(" * <i>");
		sb.append(this.complexType.getType().getName().getLocalPart());
		sb.append("</i> in name space\n");
		sb.append(" * <i>");
		sb.append(this.complexType.getType().getName().getNamespaceURI());
		sb.append("</i>.\n");
		sb.append(" * This name space is stored in file ");
		sb.append(this.config.getXsdContainerMap(
				this.complexType.getTargetNamespace()).getRelativeName());
		sb.append(".\n");
		sb.append(" * <p/>\n");
		sb.append(" * This is a ").append(this.getMappingType()).append(".\n");
		sb.append(" * <pre>");
		sb.append(Util.getGeneratedAt());
		sb.append("</pre>\n");
		sb.append(" * @author bhausen\n");
		sb.append(" */\n");
		sb.append("public interface ").append(this.interfaceName)
				.append(" {\n");

		sb.append(this.getDefaultDefinitions(""));

		sb.append("\t/**\n");
		sb.append("\t * Create a {@link ");
		sb.append(this.complexType.getClassName());
		sb.append("}.\n");
		for (ComplexTypeChild child : children) {
			sb.append("\t * @param ");
			sb.append(child.getChildName());
			sb.append(" the {@link ");
			sb.append(child.getComplexType().getClassName());
			sb.append("}.\n");
		}
		sb.append("\t * @return the {@link ");
		sb.append(this.complexType.getClassName());
		sb.append("}.\n");
		sb.append("\t */\n");

		sb.append("\t@InsightOperation\n");

		sb.append("\t").append(this.complexType.getClassName());
		sb.append(" ");
		sb.append(this.getMethodName());
		sb.append("(");
		sb.append(this.getMethodArgs(GeneratorMapper
				.getValidChildren(this.complexType)));
		sb.append(");\n");
		sb.append("}\n");
		File f = Util.getFile(outputInterfacesDirectory, this.packageName,
				new StringBuffer().append(this.interfaceName).append(".java")
						.toString());
		this.log.info(new StringBuffer().append("Write Inf  ").append(
				f.getAbsolutePath()));
		try {
			Util.writeToFile(f, sb.toString());
		} catch (Exception e) {
			this.log.error(e.getMessage());
			e.printStackTrace();
		}
		this.log.debug("-generateInterface");
		return new SimpleEntry<String, String>(this.packageName,
				this.interfaceName);
	}

	/**
	 * Get the valid {@link ComplexTypeChild} list.
	 * @return the valid {@link ComplexTypeChild} list.
	 */
	protected List<ComplexTypeChild> getValidChildren() {
		return GeneratorMapper.getValidChildren(this.complexType);
	}
}
