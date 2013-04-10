package pt.ist.ave.jzx.Instructions;

public class LD_MNNA extends Instruction {

	public LD_MNNA(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.getM_memory().write8(_cpu.getM_memory().read16(_cpu.getM_pc16()), _cpu.getM_a8());
		_cpu.setM_pc16(_cpu.incinc16(_cpu.getM_pc16()));
	}

	@Override
	public int incTstates() {
		return 13;
	}

}
