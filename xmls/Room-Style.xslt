<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" indent="yes" />

<xsl:template match="/">
   <html>
   <p><b>-ROOMS AND RESIDENTS-</b></p>
      <xsl:for-each select="Room_list/Room">
      <xsl:sort select="@id" />
      <p>
      <b>Room number:</b><xsl:value-of select="@id" /><br></br>
      <b>Room type:</b> <xsl:value-of select="@roomtype" /><br></br>
      <b>Floor:</b><xsl:value-of select="floor" /><br></br>
      <b>Gender:</b><xsl:value-of select="@gender" /><br></br>
      <b>Notes:</b><xsl:value-of select="notes" /><br></br>
      </p>
      <b>Resident:</b><br></br>
	  <table border="1">
      	<th>Name</th>
      	<th>Gender</th>
      	<th>Date of birth</th>
      	<th>Telephone</th>
     	<th>Dependency grade</th>
      	<th>Check in</th>
      	<th>Notes</th>
      	<xsl:for-each select="Residents/Resident">
      	<xsl:sort select="@name" />
            <tr>
            <td><i><xsl:value-of select="@name" /></i></td>
            <td><xsl:value-of select="@gender" /></td>
            <td><xsl:value-of select="dob" /></td>
            <td><xsl:value-of select="teleph" /></td>
            <td><xsl:value-of select="dep_grade" /></td>
            <td><xsl:value-of select="checkin" /></td>
            <td><xsl:value-of select="notes" /></td>
            </tr>
      </xsl:for-each>
      </table>
      </xsl:for-each>
   </html>
</xsl:template>

</xsl:stylesheet>