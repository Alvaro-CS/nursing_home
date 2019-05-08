<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" indent="yes" />

<xsl:template match="/">
   <html>
   <p><b><xsl:value-of select="//name" /></b></p>
   <p><b>Contents: </b><xsl:value-of select="//content" /></p>
   <p><b>Paid authors:</b></p>
   <table border="1">
      <th>Author</th>
      <th>Date of Birth</th>
      <th>Address</th>
      <xsl:for-each select="Report/Authors/Employee">
      <xsl:sort select="@name" />
         <xsl:if test="salary &gt; 0">
            <tr>
            <td><i><xsl:value-of select="@name" /></i></td>
            <td><xsl:value-of select="dob" /></td>
            <td><xsl:value-of select="address" /></td>
            </tr>
         </xsl:if>
      </xsl:for-each>
   </table>
   </html>
</xsl:template>

</xsl:stylesheet>