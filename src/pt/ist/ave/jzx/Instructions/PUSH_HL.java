package pt.ist.ave.jzx.Instructions;

public class PUSH_HL extends Instruction {

	public PUSH_HL(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.push(_cpu.hl16());
	}

	@Override
	public int incTstates() {
		return 11;
	}

}
