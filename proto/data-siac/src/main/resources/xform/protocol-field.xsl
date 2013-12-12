<!-- 
-->

<xsl:transform  
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
  xmlns:xs="http://www.w3.org/2001/XMLSchema" 
  xmlns:fun="http://function/names" 
  version="2.0" 
  > 

	<xsl:import href="protocol-common.xsl"/>



	<!-- FIELD ENTRY -->

	<xsl:template name="field-entry">
	
		<xsl:call-template name="assert-name" />
		<xsl:call-template name="assert-type" />
		
		/** <xsl:value-of select="@info" /> */
		
	    <xsl:choose>
	    
        <xsl:when test="not(@list) or @list='false'">
			<xsl:call-template name="field-entry-item" />
        </xsl:when>
        
        <xsl:when test="@list='true'">
			<xsl:call-template name="field-entry-list" />
        </xsl:when>
        
		<xsl:otherwise>
		<xsl:message terminate="yes">### error: <xsl:value-of select="@name" /> has invalid item/list attributes </xsl:message>
		</xsl:otherwise>

		</xsl:choose>
		
	</xsl:template>

	<xsl:template name="field-entry-item">
	
		<!-- primitive value/getter/setter -->
		
		private <xsl:value-of select="fun:type-name(@type, @name)" />;
		
		public  <xsl:value-of select="fun:type-name(@type, @name)" />(){
			return <xsl:value-of select="@name" />;
		}
		
		public void <xsl:value-of select="@name" />( <xsl:value-of select="fun:type-name(@type, @name)" /> ) {
			this.<xsl:value-of select="@name" /> = <xsl:value-of select="@name" />;
		}
		
		<!-- enum proxy getter/setter : use Enum suffix -->
		
		<xsl:if test="@enum">
		<xsl:call-template name="assert-is-primitive" />
		
		public <xsl:value-of select="fun:type-name(@enum, @name)" />Enum(){
			return <xsl:value-of select="@enum" />.from( <xsl:value-of select="@name" /> ) ;
		}
		
		public void <xsl:value-of select="@name" />Enum( <xsl:value-of select="fun:type-name(@enum, @name)" /> ) {
			this.<xsl:value-of select="@name" /> = <xsl:value-of select="@name" />.into_<xsl:value-of select="@type" />();
		}
		
		</xsl:if>
		
	</xsl:template>

	<xsl:template name="field-entry-list">
		private List<xsl:value-of select="fun:generic(@type)"/> <xsl:value-of select="@name" />;
		public  List<xsl:value-of select="fun:generic(@type)"/> <xsl:value-of select="@name" />(){
			return <xsl:value-of select="@name" />;
		}
		public void <xsl:value-of select="@name" />( List<xsl:value-of select="fun:generic(@type)"/> <xsl:value-of select="@name" />) {
			this.<xsl:value-of select="@name" /> = <xsl:value-of select="@name" />;
		}
	</xsl:template>



	<!-- FIELD DECODE -->

	<xsl:template name="field-decode">
		/** <xsl:value-of select="@info" /> */
		<xsl:choose>
		<xsl:when test="not(@list) or @list='false'">
		<xsl:call-template name="field-decode-item" />
		</xsl:when>
		<xsl:when test="@list='true'">
		<xsl:call-template name="field-decode-list" />
		</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="field-decode-item">
	    <xsl:choose>
        <xsl:when test="fun:is-primitive(@type)=true()">
        <xsl:call-template name="assert-codec" />
        <xsl:call-template name="assert-size" />
        <xsl:value-of select="@name" /> = codec.<xsl:value-of select="fun:codec-method(@codec,@type)" />(buffer, <xsl:value-of select="@size" />);
        </xsl:when>
		<xsl:otherwise>
		<xsl:variable name="param" select="fun:with-default(@code,'null')" />
        <xsl:value-of select="@name" /> = <xsl:value-of select="@type" />.from( buffer, <xsl:value-of select="$param" /> ); 
		</xsl:otherwise>		
		</xsl:choose>
	</xsl:template>

	<xsl:template name="field-decode-list">
	    <xsl:choose>
        <xsl:when test="@counter and not(@separator)">
        /** strategy : counter */
        final int <xsl:value-of select="@name" />$size = <xsl:value-of select="@counter" />; 
        <xsl:value-of select="@name" /> = new ArrayList<xsl:value-of select="fun:generic(@type)"/>(<xsl:value-of select="@name" />$size);
        for( int k = 0; k &lt; <xsl:value-of select="@name" />$size; k++ ) {
        	/** parse new value */
        	<xsl:value-of select="@type" /> value = <xsl:value-of select="@type" />.from( buffer, null ); 
        	<xsl:value-of select="@name" />.add(value);
        }
        </xsl:when>
        <xsl:when test="@separator and not(@counter)">
        /** strategy : separator */
        <xsl:value-of select="@name" /> = new LinkedList<xsl:value-of select="fun:generic(@type)"/>();
        while( true ) {
        	/** parse new value */
        	<xsl:value-of select="@type" /> value = <xsl:value-of select="@type" />.from( buffer, null );
        	<xsl:value-of select="@name" />.add(value);
        	/** verify separator */
        	buffer.mark();
        	if( buffer.get() == <xsl:value-of select="@separator" /> ) {
        		/** have more */
        		continue;
        	} else {
        		/** end of list */
        		buffer.reset();
        		break;
        	}
        }
        </xsl:when>
		<xsl:otherwise>
		<xsl:message terminate="yes">### error: <xsl:value-of select="@name" /> uses wrong list attributes </xsl:message>
		</xsl:otherwise>		
		</xsl:choose>
	</xsl:template>



	<!-- FIELD ENCODE -->

	<xsl:template name="field-encode">
		/** <xsl:value-of select="@info" /> */
		<xsl:choose>
		<xsl:when test="not(@list) or @list='false'">
		<xsl:call-template name="field-encode-item" />
		</xsl:when>
		<xsl:when test="@list='true'">
		<xsl:call-template name="field-encode-list" />
		</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="field-encode-item">
	    <xsl:choose>
        <xsl:when test="fun:is-primitive(@type)=true()">
        codec.<xsl:value-of select="fun:codec-method(@codec,@type)" />( <xsl:value-of select="@name" />, buffer, <xsl:value-of select="@size" /> );
        </xsl:when>
		<xsl:otherwise>
        <xsl:value-of select="@type" />.into( buffer, <xsl:value-of select="@name" /> ); 
		</xsl:otherwise>		
		</xsl:choose>
	</xsl:template>

	<xsl:template name="field-encode-list">
	    <xsl:choose>
        <xsl:when test="@counter and not(@separator)">
        /** strategy : counter */
        final int <xsl:value-of select="@name" />$size = <xsl:value-of select="@counter" />; 
        for( int k = 0; k &lt; <xsl:value-of select="@name" />$size; k++ ) {
        	/** write a value */
        	<xsl:value-of select="@type" /> value = <xsl:value-of select="@name" />.get(k) ;
        	<xsl:value-of select="@type" />.into( buffer, value ); 
        }
        </xsl:when>
        <xsl:when test="@separator and not(@counter)">
        /** strategy : separator */
        int <xsl:value-of select="@name" />$index = 0;
        int <xsl:value-of select="@name" />$size = <xsl:value-of select="@name" />.size();
        for( <xsl:value-of select="@type" /> value : <xsl:value-of select="@name" /> ){
        	/** write a value */
        	<xsl:value-of select="@type" />.into( buffer, value ); 
        	<xsl:value-of select="@name" />$index++;
        	/** write separator */
        	if( <xsl:value-of select="@name" />$index != <xsl:value-of select="@name" />$size ){
        		buffer.put( (byte) <xsl:value-of select="@separator" /> );
        	}
        }
        </xsl:when>
		<xsl:otherwise>
		<xsl:message terminate="yes">### error: <xsl:value-of select="@name" /> uses wrong list attributes </xsl:message>
		</xsl:otherwise>		
		</xsl:choose>
	</xsl:template>



	<!-- FIELD TO STRING -->

	<xsl:template name="field-to-string">
        /** <xsl:value-of select="@name" /> */
		$text.append("\n");
		$text.append(TAB( $size + 3 ));
		$text.append(&#34;<xsl:value-of select="@name" />&#34;);
		$text.append(" : ");
		<xsl:choose>
		<xsl:when test="not(@list) or @list='false'">
		<xsl:call-template name="field-to-string-item" />
		</xsl:when>
		<xsl:when test="@list='true'">
		<xsl:call-template name="field-to-string-list" />
		</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="field-to-string-item">
		<xsl:choose>
        <xsl:when test="fun:is-primitive(@type)=true() and not(@enum)">
		$text.append( <xsl:value-of select="@name" />() ); 
		</xsl:when>
        <xsl:when test="fun:is-primitive(@type)=true() and @enum">
		$text.append( <xsl:value-of select="@name" />Enum() ); 
		</xsl:when>
		<xsl:otherwise>
		if( <xsl:value-of select="@name" /> == null ){
			$text.append( "null" );
		} else {
			<xsl:value-of select="@name" />.append($text, $size + 6 );
		}
		</xsl:otherwise>		
		</xsl:choose>
	</xsl:template>

	<xsl:template name="field-to-string-list">
		if( <xsl:value-of select="@name" /> == null ){
			$text.append( "null" );
		} else {
			for( <xsl:value-of select="@type" /> value : <xsl:value-of select="@name" /> ) {
				value.append($text, $size + 3 );
			}
		}
	</xsl:template>



	<!-- FIELD SIZE -->

	<xsl:template name="field-size">
        /** <xsl:value-of select="@name" /> */
		<xsl:choose>
		<xsl:when test="not(@list) or @list='false'">
		<xsl:call-template name="field-size-item" />
		</xsl:when>
		<xsl:when test="@list='true'">
		<xsl:call-template name="field-size-list" />
		</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="field-size-item">
		<xsl:choose>
        <xsl:when test="fun:is-primitive(@type)=true()">
		size += <xsl:value-of select="@size" />; 
		</xsl:when>
		<xsl:otherwise>
		size += <xsl:value-of select="@name" />.wireSize(); 
		</xsl:otherwise>		
		</xsl:choose>
	</xsl:template>

	<xsl:template name="field-size-list">
		for( <xsl:value-of select="@type" /> entry : <xsl:value-of select="@name" /> ){
		size += entry.wireSize(); 
		}
	</xsl:template>

</xsl:transform>
