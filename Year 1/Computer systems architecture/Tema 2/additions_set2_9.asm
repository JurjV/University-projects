bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ;a - byte, b - word, c - double word, d - qword -Signed representation
    a db 8
    b dw 12
    c dd 50
    d dq 100
    

; our code starts here
segment code use32 class=code
    start:
        ;a-d+b+b+c
        mov eax,0
        mov al,[a]
        cbw
        cwde
        cdq ; EDX:EAX = a
        mov ecx,[d+0]
        mov ebx,[d+4] ; EBX:ECX = d
        clc
        sub edx,ebx
        sbb eax,ecx ; EDX:EAX = a - d
        mov ecx,0
        mov edx,0
        mov ecx,eax 
        mov ebx,edx ; EBX:ECX= a - d
        mov eax,0
        mov edx,0
        mov ax,[b]
        add ax,[b] ; AX = b + b
        cwde
        add eax,[c] 
        cdq         ;  EDX:EAX = b + b + c
        add eax,ecx
        adc edx,ebx ;  EDX:EAX = a - d + b + b + c
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
