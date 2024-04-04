bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    
    a dd 2000
    b db 12
    c db 50
    d db 20
    x dq 10000
    

; our code starts here
segment code use32 class=code
    start:
        ;a-(7+x)/(b*b-c/d+2); a-doubleword; b,c,d-byte; x-qword
        mov ecx,[x+0]
        mov ebx,[x+4]
        add ecx,7
        adc ebx,0  ; EBX:ECX = 7+x
        mov al,[b]
        imul byte [b] ; AX = b*b
        push ax
        mov eax,0
        mov al,[c]
        cbw  ; AX = c
        idiv byte [d] ; AL = c/d
        cbw
        pop dx
        sub dx,ax ; DX= b*b-c/d
        add dx,2  ; DX= b*b-c/d+2
        mov ax,dx
        cwd       ; EAX = b*b-c/d+2
        push eax
        mov eax,ecx
        mov edx,ebx ; EDX:EAX = 7+x
        pop ecx
        idiv ecx ; EAX= EDX:EAX / ECX ==> (7+x)/(b*b-c/d+2)
        mov edx,[a]
        sub edx,eax  ; EDX=a-(7+x)/(b*b-c/d+2)
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
