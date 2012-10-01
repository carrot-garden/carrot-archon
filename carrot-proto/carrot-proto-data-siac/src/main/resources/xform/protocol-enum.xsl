<!-- 
-->

<xsl:transform  
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
  xmlns:xs="http://www.w3.org/2001/XMLSchema" 
  xmlns:fun="http://function/names" 
  version="2.0" 
  > 

	<xsl:import href="protocol-common.xsl"/>

	<!-- ENUM -->
	
	<xsl:template match="/protocol/enum">
	
		<xsl:call-template name="assert-name" />
		<xsl:call-template name="assert-codec" />
		
		<xsl:variable name="name" select="@name" />
		
		/** 
		* codec=<xsl:value-of select="@codec" />; 
		* <xsl:value-of select="@info" /> 
		*/
		
		public static enum <xsl:value-of select="@name" /> 
		{
		
		<xsl:for-each select="/protocol/enum[@name=$name]/value">
			/** <xsl:value-of select="@info" /> */
			<xsl:value-of select="@name" />
			<xsl:value-of select="concat( '( &#34;', @code, '&#34; )')" /> , //
		</xsl:for-each>

		;

		private final static Logger log = LoggerFactory.getLogger(<xsl:value-of select="@name" />.class);

		/** string representation of enum value */
		public final String code;

		private <xsl:value-of select="@name" /> (String code) {
			this.code = code;
		}

		private static final <xsl:value-of select="@name" />[] ENUM_VALS = values();

		/** find by string code; returns customer-defined value UNKNOWN for unknown code */
		public static final <xsl:value-of select="@name" /> from (String code) {
			for( <xsl:value-of select="@name" /> known : ENUM_VALS) {
				if(known.code.equals(code)) { return known; }
			}
			if(log.isDebugEnabled()){
				log.debug("unknown code", new IllegalArgumentException(code));
			}
			return UNKNOWN;
		}

	    <xsl:choose>
        <xsl:when test="@codec='number'">
			public static final <xsl:value-of select="@name" /> from (int code) {
		        return from ( Integer.toString( code ) );
			}
			public byte into_byte(){
				return Byte.parseByte(code);
			}
			public int into_int(){
				return Integer.parseInt(code);
			}
        </xsl:when>
        <xsl:when test="@codec='string'">
			public static final <xsl:value-of select="@name" /> from (int code) {
		        return from ( new String( new char[]{(char) code } ) );
			}
			public byte into_byte(){
				return (byte) code.charAt(0);
			}
			public int into_int(){
				return (int) code.charAt(0);
			}
        </xsl:when>
		<xsl:otherwise>
		<xsl:message terminate="yes">### error: <xsl:value-of select="@name" /> uses unknown codec : <xsl:value-of select="@codec" /> </xsl:message>
		</xsl:otherwise>		
		</xsl:choose>

		public String into_String(){
			return code;
		}

		@Override
		public String toString(){
			return name() + "(" + code + ")" ;
		}

		}
	</xsl:template>

</xsl:transform>
