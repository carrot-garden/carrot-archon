<!-- 
-->

<xsl:transform  
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
  xmlns:xs="http://www.w3.org/2001/XMLSchema" 
  xmlns:fun="http://function/names" 
  version="2.0" 
  > 

	<xsl:import href="protocol-common.xsl"/>
	<xsl:import href="protocol-enum.xsl"/>
	<xsl:import href="protocol-record.xsl"/>
	<xsl:import href="protocol-union.xsl"/>

	<!-- root folder for generated files -->
	<xsl:param name="java_target" />

	<xsl:template match="/protocol">

		<xsl:variable name="java_main" select="config/main/@class"/>
		<xsl:variable name="java_codec" select="config/codec/@class"/>
		<xsl:variable name="java_converter" select="config/converter/@class"/>

		<xsl:variable name="java_path" 
			select="replace($java_main,'\.','/')"/>

		<xsl:variable name="java_output" 
			select="concat( $java_target, '/', $java_path, '.java' )"/>

		<xsl:result-document method="text" encoding="UTF-8" href="{$java_output}">
		
		/** 
		*
		* note: generated file, do not edit;
		*
		* <xsl:value-of select="config/@info" />
		*
		*/
		package <xsl:value-of select="fun:package-name($java_main)" />;

		import java.nio.ByteBuffer;
		
		import java.util.List;
		import java.util.ArrayList;
		import java.util.LinkedList;

		import org.slf4j.Logger;
		import org.slf4j.LoggerFactory;
		
		import com.carrotgarden.proto.data.MarketPacket;
		import com.carrotgarden.proto.data.MarketMessage;
		import com.carrotgarden.proto.data.MarketEntry;
		
		/** protocol module entry point */
		public final class <xsl:value-of select="fun:class-name($java_main)" />
		{

		/** field codec specific to the protocol */
		private static <xsl:value-of select="$java_codec" /> codec;
		public static void bind(<xsl:value-of select="$java_codec" /> codec) {
			<xsl:value-of select="$java_main" />.codec = codec;
		}
		
		/** node converter specific to the protocol */
		private static <xsl:value-of select="$java_converter" /> converter;
		public static void bind(<xsl:value-of select="$java_converter" /> converter) {
			<xsl:value-of select="$java_main" />.converter = converter;
		}
		
		/** interface implemented by records and unions */
		public static interface Node <xsl:value-of select="fun:generic('T')" /> {
		
			/** decode fields from buffer */
			void from(ByteBuffer buffer) throws Exception;
			
			/** encode fields into buffer */
			void into(ByteBuffer buffer) throws Exception;
			
			/** report declared size */
			int testSize();
			
			/** calculate live encoded size */
			int wireSize();
			
			/** pretty-print : append fields to text */
			void append(StringBuilder text, int tab);
			
			/** convert form an element of another protocol */
			void from(T element) throws Exception;

			/** convert into an element of another protocol */
			void into(T element) throws Exception;
					
		}

		<xsl:apply-templates select="/protocol/enum" />
		<xsl:apply-templates select="/protocol/record" />
		<xsl:apply-templates select="/protocol/union" />
		
		/** tab builder for pretty-print */
		public static String TAB(int size) {
			return new String(new char[size]).replace("\0", " ");
		}

		}
		
		</xsl:result-document>

	</xsl:template>

</xsl:transform>
