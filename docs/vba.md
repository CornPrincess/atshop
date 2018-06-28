删除空白行和列的方法：

步骤一、打开excel，按ALT+F11组合建，调出VBA程序窗口

步骤二、在插入菜单中，选择模块，插入一个模块

步骤三、在新建模块中的代码窗口将以下代码复制进去

‘删除空行

<pre>
Sub DeleteEmptyRows()
    Dim LastRow As Long, r As Long
    LastRow = ActiveSheet.UsedRange.Rows.Count
    LastRow = LastRow + ActiveSheet.UsedRange.Row -1

    For r = LastRow To 1 Step -1
        If WorksheetFunction.CountA(Rows(r)) = 0 Then Rows(r).Delete
    Next r
End Sub
</pre>

’删除空列
<pre>
Sub DeleteEmptyColumns()
    Dim LastColumn As Long, c As Long
    LastColumn = ActiveSheet.UsedRange.Columns.Count
    LastColumn = LastColumn + ActiveSheet.UsedRange.Column
    For c = LastColumn To 1 Step -1
        If WorksheetFunction.CountA(Columns(c)) = 0 Then Columns(c).Delete
    Next c
End Sub
</pre>


完成后关闭VBA窗口，选择保存，并保存Excel文件。

步骤四、保持步骤三中的excel文件处于打开状态，打开需要处理excel文件，执行上述两个宏即可。


用VBA删除空行和空列 
        
    在Excel中删除空行和空列的方法有很多，下面的方法是用VBA代码来删除工作表指定区域中的空行和空列：

Option Explicit
 
Sub Delete_Empty_Rows()
   Dim rnArea As Range
   Dim lnLastRow As Long, i As Long, j As Long
 
   Application.ScreenUpdating = False
   lnLastRow = Selection.Rows.Count
   Set rnArea = Selection
 
   j = 0
 
   For i = lnLastRow To 1 Step -1
      If Application.CountA(rnArea.Rows(i)) = 0 Then
         rnArea.Rows(i).Delete
         j = j + 1
      End If
   Next i
 
   rnArea.Resize(lnLastRow - j).Select
 
   Application.ScreenUpdating = True
End Sub
 
Sub Delete_Empty_Columns()
   Dim lnLastColumn As Long, i As Long, j As Long
   Dim rnArea As Range
 
   Application.ScreenUpdating = False
   lnLastColumn = Selection.Columns.Count
   Set rnArea = Selection
 
   j = 0
 
   For i = lnLastColumn To 1 Step -1
      If Application.CountA(rnArea.Columns(i)) = 0 Then
         rnArea.Columns(i).Delete
         j = j + 1
      End If
   Next i
 
   rnArea.Resize(, lnLastColumn - j).Select
 
   Application.ScreenUpdating = False
End Sub

    在运行代码前，先选择需要删除空行和空列的区域。如果要删除空行，则运行Delete_Empty_Rows()，指定区域中的空行将被删除。注意所谓空行是在指定区域中的，如果在指定区域内是

空行，而在指定区域外不是空行，那么这些空行还是将会被删除。删除空列则运行Delete_Empty_Columns()。


检查ip地址
<pre>
Private Sub Worksheet_Change(ByVal Target As Range)
    'IPのチェック
     Dim RegEx As Object
     Set RegEx =CreateObject("vbscript.regexp")
    RegEx.Global = True
    RegEx.Pattern ="^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$"
   
     If Not (RegEx.Test(Target.Text))Then
     Target.Characters(Start:=1,Length:=Len(Target.Text)).Font.ColorIndex = 3
      MsgBox ("IPアドレスは違います。")
     End If
End Sub
</pre>

<pre>

Public Function IsValidIP(ByVal StrIP As String) As Boolean
    Dim arr
    Dim i
    arr = Split(StrIP, ".")
    If UBound(arr) <> 3 Then
    IsValidIP = False
    Exit Function
    Else
    For i = 0 To 3
    If IsNumeric(arr(i)) = False Then
    IsValidIP = False
    Exit Function
    End If
    If Val(arr(i)) > 255 Then
    IsValidIP = False
    Exit Function
    End If
    Next
    IsValidIP = True
    End If
End Function
</pre>