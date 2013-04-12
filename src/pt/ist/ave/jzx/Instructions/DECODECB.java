package pt.ist.ave.jzx.Instructions;

//special instruction
public class DECODECB extends Instruction {

	public DECODECB(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		
		int op8 = _cpu.mone8();
		_cpu.decodeCB(op8);
	}

	@Override
	public int incTstates() {
		return 0;
	}

}
