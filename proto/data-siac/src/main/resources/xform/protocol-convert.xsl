<!-- 
-->

<xsl:transform xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fun="http://function/names"
	version="2.0">

	<xsl:import href="protocol-common.xsl" />

	<xsl:template name="record-convert-into">

		<xsl:variable name="record" select="." />
	
		<xsl:if test="not(convert)">
			throw new IllegalStateException("missing convert definition");
		</xsl:if>
	
		<xsl:for-each select="convert[1]">

			<xsl:call-template name="assert-type" />
			<xsl:call-template name="assert-name" />

			/** accept 1 */
			
			<xsl:value-of select="into/text()[1]" />

			<xsl:for-each select="into/visit[1]">

				/** visit */

				<xsl:call-template name="assert-name" />

				<xsl:variable name="param" select="@name" />

				<xsl:for-each select="$record/field">

				    <xsl:choose>
			        <xsl:when test="fun:is-primitive(@type)=true()">
			        </xsl:when>
			        <xsl:when test="@list">
			        	for( <xsl:value-of select="@type" /> entry : <xsl:value-of select="@name" /> ){
			        		entry.into( <xsl:value-of select="$param" /> );
			        	}
			        </xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="@name" />.into( <xsl:value-of select="$param" /> );
					</xsl:otherwise>		
					</xsl:choose>

				</xsl:for-each>

			</xsl:for-each>

			/** accept 2 */
			
			<xsl:value-of select="into/text()[2]" />
			
		</xsl:for-each>

	</xsl:template>

	<xsl:template name="union-convert-into">
	
		<xsl:if test="not(convert)">
			throw new IllegalStateException("missing convert definition");
		</xsl:if>
	
		<xsl:for-each select="convert[1]">

			<xsl:call-template name="assert-name" />
			<xsl:call-template name="assert-type" />

			/** accept 1 */		

			<xsl:value-of select="into/text()[1]" />

			<xsl:for-each select="into/visit[1]">

				/** visit */		
			
				<xsl:call-template name="assert-name" />

				value.into( <xsl:value-of select="@name" /> );

			</xsl:for-each>

			/** accept 2 */		

			<xsl:value-of select="into/text()[2]" />
			
		</xsl:for-each>

	</xsl:template>

	<xsl:template name="record-convert-from">
		throw new UnsupportedOperationException("TODO");
	</xsl:template>

	<xsl:template name="union-convert-from">
		throw new UnsupportedOperationException("TODO");
	</xsl:template>

</xsl:transform>
