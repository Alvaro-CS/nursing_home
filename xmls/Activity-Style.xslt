<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" indent="yes" />

<xsl:template match="/">
   <html>
   <p><b>-ACTIVITIES-</b></p>
    
	  <table border="1">
      	<th>Name</th>
      	<th>Hours</th>
      	<th>Days</th>
      	<th>Location</th>
      	 <xsl:for-each select="Activity_list/Activity">
      	<xsl:sort select="@name" />
            <tr>
            <td><i><xsl:value-of select="@name" /></i></td>
            <td><xsl:value-of select="@hours" /></td>
            <td><xsl:value-of select="@days" /></td>
            <td><xsl:value-of select="@location" /></td>
            </tr>
      </xsl:for-each>

      </table>
   </html>
</xsl:template>

</xsl:stylesheet>