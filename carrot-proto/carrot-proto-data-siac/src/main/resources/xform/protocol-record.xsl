<!-- 
-->

<xsl:transform  
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
  xmlns:xs="http://www.w3.org/2001/XMLSchema" 
  xmlns:fun="http://function/names" 
  version="2.0" 
  > 

	<xsl:import href="protocol-common.xsl"/>
	<xsl:import href="protocol-convert.xsl"/>
	<xsl:import href="protocol-field.xsl"/>

	<!-- RECORD -->
	
	<xsl:template match="/protocol/record">
	
		<xsl:call-template name="assert-name" />
		
		<xsl:variable name="name" select="@name" />
		
		<xsl:variable name="convert-type" select="fun:with-default(convert/@type,'Object')" />
		<xsl:variable name="convert-name" select="fun:with-default(convert/@name,'instance')" />
		
		/** 
		record <xsl:value-of select="@info" /> 
		*/
		public static class <xsl:value-of select="@name" /> 
		implements Node <xsl:value-of select="fun:generic($convert-type)" />
		{
		
		@Override
		public void from( <xsl:value-of select="fun:type-name($convert-type,$convert-name)" /> ) throws Exception {
			<xsl:call-template name="record-convert-from"/>
		}

		@Override
		public void into( <xsl:value-of select="fun:type-name($convert-type,$convert-name)" /> ) throws Exception {
			<xsl:call-template name="record-convert-into"/>
		}
		
		/////////////////////////////
		
		<xsl:for-each select="field">
		<xsl:call-template name="field-entry" />
		</xsl:for-each>

		@Override
		public void from( final ByteBuffer buffer ) throws Exception {
			<xsl:for-each select="field">
			<xsl:call-template name="field-decode" />
			</xsl:for-each>
		}

		@Override
		public void into( final ByteBuffer buffer ) throws Exception {
			<xsl:for-each select="field">
			<xsl:call-template name="field-encode" />
			</xsl:for-each>
		}
		
		public static <xsl:value-of select="@name" /> from( ByteBuffer buffer, Object none ) throws Exception {
			<xsl:value-of select="@name" /> record = new <xsl:value-of select="@name" />();
			record.from(buffer);
			return record; 
		}
		
		public static void into (ByteBuffer buffer, <xsl:value-of select="@name" /> record ) throws Exception {
			record.into(buffer);
		}
		
		/** record test size */
		public int testSize() { 
		    <xsl:choose>
	        <xsl:when test="@size">
			/** report declared size */
			return <xsl:value-of select="@size" />; 
	        </xsl:when>
			<xsl:otherwise>
			/** size declaration missing */
			return 0; 
			</xsl:otherwise>		
			</xsl:choose>
		}
		
		/** record wire size */
		@Override
		public int wireSize() { 
			int size = 0;
			<xsl:for-each select="field">
			<xsl:call-template name="field-size" />
			</xsl:for-each>
			return size;
		}

		@Override
		public String toString(){
			StringBuilder text = new StringBuilder(128);
			append(text, 0);
			return text.toString();
		}
		
		@Override
		public void append(StringBuilder $text, int $size){
			/** start */
			$text.append("\n");
			$text.append(TAB($size)); 
			$text.append("{");
			<xsl:for-each select="field">
			<xsl:call-template name="field-to-string" />
			</xsl:for-each>
			/** finish */
			$text.append("\n");
			$text.append(TAB($size)); 
			$text.append("}");
		}

		}
		
	</xsl:template>

</xsl:transform>
