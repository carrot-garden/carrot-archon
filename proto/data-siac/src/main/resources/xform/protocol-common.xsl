<!-- 
-->

<xsl:transform  
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
  xmlns:xs="http://www.w3.org/2001/XMLSchema" 
  xmlns:fun="http://function/names" 
  version="2.0" 
  > 

	<!-- FUNCTIONS -->

	<!-- determine if supported primitive type -->
	<xsl:function name="fun:is-primitive">
	    <xsl:param name="type" />
		<xsl:choose>
        <xsl:when test="$type='byte' or $type='int' or $type='long' or $type='String'">
        <xsl:value-of select="true()" />
		</xsl:when>
		<xsl:otherwise>
        <xsl:value-of select="false()" />
		</xsl:otherwise>		
		</xsl:choose>
	</xsl:function>

	<!-- node value if present or default -->
	<xsl:function name="fun:with-default">
	    <xsl:param name="node"/>
	    <xsl:param name="value"/>
		<xsl:choose>
        <xsl:when test="$node">
        <xsl:value-of select="$node" />
		</xsl:when>
		<xsl:otherwise>
        <xsl:value-of select="$value" />
		</xsl:otherwise>		
		</xsl:choose>
	</xsl:function>

	<!-- provide "type name" stanza -->
	<xsl:function name="fun:type-name">
	    <xsl:param name="type"/>
	    <xsl:param name="name"/>
	    <xsl:value-of select="$type" /><xsl:text> </xsl:text><xsl:value-of select="$name" />
	</xsl:function>

	<!-- provide type in brackets stanza -->
	<xsl:function name="fun:generic">
	    <xsl:param name="type"/>
	    <xsl:value-of select="concat(' ', '&lt;', $type, '&gt;', ' ')" />
	</xsl:function>

	<!-- codec method naming convention -->
	<xsl:function name="fun:codec-method">
	    <xsl:param name="codec"/>
	    <xsl:param name="type"/>
	    <xsl:value-of select="$codec" />_<xsl:value-of select="$type" />
	</xsl:function>

	<xsl:function name="fun:file-name">
	    <xsl:param name="file"/>
	    <xsl:sequence select="tokenize($file, '\.')[1]" />
	</xsl:function>

	<xsl:function name="fun:file-extension">
	    <xsl:param name="file"/>
	    <xsl:sequence select="tokenize($file, '\.')[last()]" />
	</xsl:function>

	<xsl:function name="fun:base-name">
	    <xsl:param name="path"/>
	    <xsl:sequence select="tokenize($path, '/')[last()]" />
	</xsl:function>

	<xsl:function name="fun:dir-name">
	    <xsl:param name="path"/>
	    <xsl:sequence select="string-join(tokenize($path, '/')[position() != last()], '/')" />
	</xsl:function>
	
	<xsl:function name="fun:package-name">
	    <xsl:param name="full-name"/>
	    <xsl:sequence select="string-join(tokenize($full-name, '\.')[position() != last()], '.')" />
	</xsl:function>

	<xsl:function name="fun:class-name">
	    <xsl:param name="full-name"/>
	    <xsl:sequence select="tokenize($full-name, '\.')[last()]" />
	</xsl:function>

	
	<xsl:function name="fun:assert">
	    <xsl:param name="value"/>
	    <xsl:param name="message"/>
		<xsl:if test="not($value)">
		<xsl:message terminate="yes"><xsl:value-of select="$message" /></xsl:message>
		</xsl:if>		
	</xsl:function>
	
	<xsl:template name="assert-name">
		<xsl:if test="not(@name)">
		<xsl:message terminate="yes">### error: <xsl:value-of select="name()" /> is missing 'name' attribute</xsl:message>
		</xsl:if>		
	</xsl:template>
	
	<xsl:template name="assert-type">
		<xsl:if test="not(@type)">
		<xsl:message terminate="yes">### error: <xsl:value-of select="@name" /> is missing 'type' attribute</xsl:message>
		</xsl:if>		
	</xsl:template>
	
	<xsl:template name="assert-codec">
		<xsl:if test="not(@codec)">
		<xsl:message terminate="yes">### error: <xsl:value-of select="@name" /> is missing 'codec' attribute</xsl:message>
		</xsl:if>		
	</xsl:template>
	
	<xsl:template name="assert-size">
		<xsl:if test="not(@size)">
		<xsl:message terminate="yes">### error: <xsl:value-of select="@name" /> is missing 'size' attribute</xsl:message>
		</xsl:if>		
	</xsl:template>
	
	<xsl:template name="assert-code">
		<xsl:if test="not(@code)">
		<xsl:message terminate="yes">### error: <xsl:value-of select="@name" /> is missing 'code' attribute</xsl:message>
		</xsl:if>		
	</xsl:template>
	
	<xsl:template name="assert-param">
		<xsl:if test="not(@param)">
		<xsl:message terminate="yes">### error: <xsl:value-of select="@name" /> is missing 'param' attribute</xsl:message>
		</xsl:if>		
	</xsl:template>
	
	<xsl:template name="assert-is-primitive">
		<xsl:if test="not(fun:is-primitive(@type))">
		<xsl:message terminate="yes">### error: <xsl:value-of select="@name" /> must be a primitive type </xsl:message>
		</xsl:if>		
	</xsl:template>

</xsl:transform>
