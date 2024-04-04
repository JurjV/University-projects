bits 32
global  start 

extern  exit
import  exit  msvcrt.dll
        
segment  data use32 class=data 
    a db 17
    b db 15
    c db 7
    d db 12
    e dw 1156
    f dw 1000
    g dw 5
    h dw 8
    
segment  code use32 class=code 
start: 
	;f*(e-2)/[3*(d-5)]
    mov eax,0
	mov edx,0
    mov ax,[e]
    sub ax,2
    mov dx,[f]
    mul dx    ;dx*ax= dx*ax= f*(e-2)
    mov ebx,eax
    mov eax,0
    mov al,[d]
    sub al,5
    mov ah,3
    mul ah     ;ax=ah*al = 3*(d-5)
    mov ecx,0
    mov cx,ax
    mov eax,0
    mov eax,ebx
    div cx
    
    
    
	
	push   dword 0 
	call   [exit] 
    