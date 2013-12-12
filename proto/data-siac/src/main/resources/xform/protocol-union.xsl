<!-- 
-->

<xsl:transform  
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
  xmlns:xs="http://www.w3.org/2001/XMLSchema" 
  xmlns:fun="http://function/names" 
  version="2.0" 
  > 

	<!-- UNION -->
	
	<xsl:template match="/protocol/union">
	
		<xsl:call-template name="assert-name" />
		
		<xsl:variable name="name" select="@name" />

		<xsl:variable name="convert-type" select="fun:with-default(convert/@type,'Object')" />
		<xsl:variable name="convert-name" select="fun:with-default(convert/@name,'instance')" />
		
		/** 
		* union record <xsl:value-of select="@info" /> of mulitple optional types 
		*/
		public static class <xsl:value-of select="@name" /> 
		implements Node <xsl:value-of select="fun:generic($convert-type)" />
		{

		@Override
		public void from( <xsl:value-of select="fun:type-name($convert-type,$convert-name)" /> ) throws Exception {
			<xsl:call-template name="union-convert-from"/>
		}

		@Override
		public void into( <xsl:value-of select="fun:type-name($convert-type,$convert-name)" /> ) throws Exception {
			<xsl:call-template name="union-convert-into"/>
		}


		////////////////////////////
		
		/** 
		* union option type selector for <xsl:value-of select="@name" /> 
		*/
		public static enum Type
		{
		
		<!-- enum value entries -->
		<xsl:for-each select="option">
		
			<xsl:call-template name="assert-name" />
			<xsl:call-template name="assert-type" />
			<xsl:call-template name="assert-code" />
			
			/** <xsl:value-of select="@info" /> */
			<xsl:value-of select="@name" />
			<xsl:value-of select="concat( '( ', @type, '.class', ', ', '&#34;', @code, '&#34;', ' )')" />
			{
			public Node newInstance() { 
				return new <xsl:value-of select="@type" />(); 
			}
			public <xsl:value-of select="fun:generic('T')" /> void apply( Visitor<xsl:value-of select="fun:generic('T')" /> visitor, Node instance, T context ) { 
				visitor.apply( (<xsl:value-of select="@type" />) instance, context );
			}
			}, //
			
		</xsl:for-each>
	
		;

		private final static Logger log = LoggerFactory.getLogger(Type.class);

		/** java class representation of union type */
		public final Class<xsl:value-of select="fun:generic('? extends Node')" /> klaz;
		
		/** string code representation of union type */
		public final String code;

		private Type (Class<xsl:value-of select="fun:generic('? extends Node')" /> klaz, String code) {
			this.klaz = klaz;
			this.code = code;
		}

		private static final Type[] ENUM_VALS = values();

		/** find by string code; returns null for unknown code */
		public static final Type from ( String code ) {
			for( Type known : ENUM_VALS) {
				if(known.code.equals(code))  { return known; }
			}
			if(log.isDebugEnabled()){
				log.debug("unknown code", new IllegalArgumentException(code));
			}
			return null;
		}
		
		/** make new instance of specific union option type */
		public abstract Node newInstance();

		/** apply visitor for provided union option via compile-time cast */
		public abstract <xsl:value-of select="fun:generic('T')" /> void apply( Visitor<xsl:value-of select="fun:generic('T')" /> visitor, Node option, T context );

		public void append(StringBuilder text, int size){
			text.append("\n");
			text.append(TAB(size));
			text.append("// type : ");
			text.append(name());
			text.append("(");
			text.append(klaz.getSimpleName());
			text.append(",");
			text.append(code);
			text.append(")");
		}

		}
		
		///////////////////////////

		/** visitor for <xsl:value-of select="@name" /> union option types */
		public static interface Visitor<xsl:value-of select="fun:generic('T')" />
		{
		<xsl:for-each select="option[not(./@type=preceding-sibling::option/@type)]">
			/** <xsl:value-of select="@info" />  */
			void apply( <xsl:value-of select="@type" /> option, T context );
		</xsl:for-each>
		}

		///////////////////////////

		/** union option type selector */
		private Type type;
		public  Type type() { return type; }
		public  void type( Type type ) { this.type = type; }

		/** union option stored value */
		private Node<xsl:value-of select="fun:generic($convert-type)" /> value;
		public  Node<xsl:value-of select="fun:generic($convert-type)" /> value() { return value; }
		public  void  value( Node<xsl:value-of select="fun:generic($convert-type)" /> value ) { this.value = value; }

		@Override
		public void from( final ByteBuffer buffer ) throws Exception {
			value.from(buffer);
		}

		@Override
		public void into( final ByteBuffer buffer ) throws Exception {
			value.into(buffer);
		}
		
		/** decode: make new union option instance based on code, then decode union option from buffer */
		public static <xsl:value-of select="@name" /> from( ByteBuffer buffer, String code ) throws Exception {
			Type type = Type.from(code);
			if( type == null ){
				throw new IllegalArgumentException("unknown union option code=" + code);
			}
			<xsl:value-of select="@name" /> union = new <xsl:value-of select="@name" />();
			union.type = type;
			union.value = type.newInstance();
			union.from(buffer);
			return union;
		}

		/** encode */
		public static void into ( ByteBuffer buffer, <xsl:value-of select="@name" /> union ) throws Exception {
			union.into(buffer);
		}
		
		/** union option test size */
		public int testSize() {
			return value.testSize();
		} 

		/** union option wire size */
		@Override
		public int wireSize() { 
			return value.wireSize();
		}

		@Override
		public String toString(){
			StringBuilder text = new StringBuilder(128);
			append(text, 0);
			return text.toString();
		}
		
		@Override
		public void append(StringBuilder text, int size){
			type.append(text, size);
			value.append(text, size );
		}

		}
		
	</xsl:template>

</xsl:transform>
