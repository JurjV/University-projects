bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    
    a db 8
    b db 12
    c dw 50
    e dd 200
    x dq 1000
    

; our code starts here
segment code use32 class=code
    start:
        ;(a-b+c*128)/(a+b)+e-x; a,b-byte; c-word; e-doubleword; x-qword
        mov ax,[c]
        mov bx,128
        imul bx   ; DX:AX = c*128
        mov bx,dx
        mov cx,ax  ; BX:CX = c*128
        mov eax,0
        mov edx,0
        mov al,[a]
        sub al,[b] 
        cbw
        cwd         ;  DX:AX= a-b
        add dx,bx
        add ax,cx        ;  DX:AX= a-b+c*128
        mov dx,0
        mov bx,dx
        mov cx,ax   ;  BX:CX= a-b+c*128
        mov al,[a]
        add al,[b]
        cbw         ; AX = a+b
        push ax
        mov dx,bx
        mov ax,cx  ; DX:AX= a-b+c*128
        mov ecx,0
        pop cx
        idiv cx 
        cwde        ; EAX = (a-b+c*128)/(a+b)
        add eax,[e] ; EAX = (a-b+c*128)/(a+b)+e
        cdq         ; EDX:EAX = (a-b+c*128)/(a+b)+e
        mov ecx,[x+0]
        mov ebx,[x+4]
        sub edx,ebx
        sub eax,ecx ; EDX:EAX = (a-b+c*128)/(a+b)+e-x
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
